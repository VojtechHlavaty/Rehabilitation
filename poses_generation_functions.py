import numpy as np
import open3d as o3d
import plotly.graph_objects as go
from scipy.spatial.transform import Rotation
import matplotlib.pyplot as plt
from scipy.spatial import ConvexHull
# import IK_6DOF as ik
# import FK_6DOF as fk
# import workspace_calibration_visualization as trafo_visualization


def erase_sphere_from_pcd(pcd, center, Rot):
    points = np.asarray(pcd.points)

    # Calculate squared distances to the center
    distances_sq = np.sum((points - center) ** 2, axis=1)
    
    # Find indices of points that are outside the sphere
    inside_indices = np.where(distances_sq >= Rot**2)[0]

    # Extract points that are inside the sphere
    outside_points = points[inside_indices]

    # Create a new point cloud from the points inside the sphere
    outside_pcd = o3d.geometry.PointCloud()
    outside_pcd.points = o3d.utility.Vector3dVector(outside_points)

    return outside_pcd

def erase_sphere_from_points(points, center, Rot):
    distances_sq = np.sum((points - center) ** 2, axis=1)
    
    # Find indices of points that are outside the sphere
    inside_indices = np.where(distances_sq >= Rot**2)[0]

    # Extract points that are inside the sphere
    outside_points = points[inside_indices]
    return outside_points

def generate_path_zig_zag(pcd, stride = 75, radius = 50, tolerance = 5):
    # radius - radius of the sphere to erase 
    # stride - stride of the path that will be generated 
    
    points = np.asarray(pcd.points)

    # Find the index of the most down point
    index = np.argmin(points[:, 0])

    # Extract the most left point
    most_left = points[index] # this will be the starting point of the path
    path = [most_left]
    going_up = True
    while points.size != 0:
        current_point = path[-1]
        points = erase_sphere_from_points(points, current_point, radius)
        if going_up:
            stripe = points[np.where( np.abs(points[:, 0] - current_point[0]) < tolerance)] # get all points with same y coordinate with some tolerance
            if stripe.size == 0: # if there are no points in the stripe, go down 
                going_up = False
                stripe = points[np.where( np.abs(points[:, 0] - (current_point[0] + stride) ) < tolerance)]
                if stripe.size == 0:
                    break
                next = stripe[np.argmax(stripe[:, 1])]
                path.append(next)
            else:
                next = stripe[np.argmin(stripe[:, 1])]
                path.append(next)
        else: # going down
            stripe = points[np.where( np.abs(points[:, 0] - current_point[0]) < tolerance)]
            if stripe.size == 0: # if there are no points in the stripe, go up
                going_up = True
                stripe = points[np.where( np.abs(points[:, 0] - (current_point[0] + stride)) < tolerance)]
                if stripe.size == 0:
                    break
                next = stripe[np.argmin(stripe[:, 1])]
                path.append(next)
            else:
                next = stripe[np.argmax(stripe[:, 1])]
                path.append(next)
    path = np.asarray(path)

    # add to the path their orientations 

    path_with_orientations = np.zeros((path.shape[0], 4,4)) # array of transformation matrices
    point_array = np.asarray(pcd.points)

    for index, p in enumerate(path):
        n_index = np.where(np.all(point_array == path[index], axis=1))
        if n_index[0].size:
            n = pcd.normals[n_index[0][0]]
            n = n / np.linalg.norm(n)            
        else:
            print("ERROR: Point not found in the point cloud!")
            break
        x = np.array([-1, 0, 0]) # project vector [-1 0 0 ] to a plane that is defined by a normal vector
        x_proj = x - np.dot(x, n) * n
        x_proj = x_proj / np.linalg.norm(x_proj)
        
        y = np.cross(n, x_proj)
        y = y / np.linalg.norm(y)

        path_with_orientations[index, :3, :3] = np.column_stack((x_proj, y, n)) # this creates a rotation matrix from the x, y and n vectors
        path_with_orientations[index, :3, 3] = p
        path_with_orientations[index, 3, 3] = 1
    return path_with_orientations

def plot_coordinate_system(ax, origin, R, axis_length = 200):
    axis = np.array([[0, 0, 0, 1],
                     [axis_length, 0, 0, 1],
                     [0, axis_length, 0, 1],
                     [0, 0, axis_length, 1]])
 
    transformed_axis = R.dot(axis.T).T
    ax.quiver(origin[0], origin[1], origin[2],
              transformed_axis[:, 0] - origin[0],
              transformed_axis[:, 1] - origin[1],
              transformed_axis[:, 2] - origin[2], color=['r', 'g', 'b'])

def plot_coordinate_systems_matplotlib(trafos):
    fig = plt.figure(figsize=(8, 8))
    ax = fig.add_subplot(111, projection='3d')
    points = []
    for T in trafos:
        T = np.array(T)
        origin = T[:3, 3]
        plot_coordinate_system(ax, origin, T, axis_length = 20)
        points.append(origin)
    points = np.array(points)
    ax.plot(points[:, 0], points[:, 1], points[:, 2], color='black', linewidth=1)
    ax.set_xlim([-100, 2000])
    ax.set_ylim([-1000, 1000])
    ax.set_zlim([-1000, 0])
    ax.set_xlabel('X')
    ax.set_ylabel('Y')
    ax.set_zlabel('Z')

    plt.show()



# Visualization of generated path with orientations, taht means the path is represented by a series of coordinate systems
def plot_coordinate_systems(T_list, length=1.0): 
    fig = go.Figure()

    for T in T_list:
        # Extract the origin from the transformation matrix
        origin = [T[0][3], T[1][3], T[2][3]]
        
        # Extract the basis vectors
        x_axis = [T[0][0], T[1][0], T[2][0]]
        y_axis = [T[0][1], T[1][1], T[2][1]]
        z_axis = [T[0][2], T[1][2], T[2][2]]
        
        # Create lines for the basis vectors
        lines = [
            (origin, [origin[i] + length*x_axis[i] for i in range(3)], 'red'),
            (origin, [origin[i] + length*y_axis[i] for i in range(3)], 'green'),
            (origin, [origin[i] + length*z_axis[i] for i in range(3)], 'blue')
        ]
        
        # Add lines to the figure
        for start, end, color in lines:
            fig.add_trace(go.Scatter3d(x=[start[0], end[0]], 
                                       y=[start[1], end[1]], 
                                       z=[start[2], end[2]], 
                                       mode='lines',
                                       line=dict(color=color, width=10)))

    fig.show()

def transform_matrices_to_coordinates(matrices):
    """
    Convert a list of 4x4 transformation matrices to a list of coordinates [X, Y, Z, yaw, pitch, roll].
    
    Parameters:
    matrices (list of numpy.ndarray): List of 4x4 transformation matrices.
    
    Returns:
    list of lists: Each sublist contains [X, Y, Z, yaw, pitch, roll] for a transformation matrix.
    """
    coordinates = []
    for matrix in matrices:
        X, Y, Z = matrix[0:3, 3]
        rotation_matrix = matrix[0:3, 0:3]
        euler_angles = Rotation.from_matrix(rotation_matrix).as_euler('zyx', degrees=False)
        yaw, pitch, roll = euler_angles
        coordinates.append([X, Y, Z, yaw, pitch, roll])
    return coordinates

def transform_matrices_to_points(matrices):
    """
    Just helper function
    """
    coordinates = []
    for matrix in matrices:
        X, Y, Z = matrix[0:3, 3]
        coordinates.append(X)
        coordinates.append(Y)
        coordinates.append(Z)
    while len(coordinates) < 175*3:
        coordinates.append(X)
        coordinates.append(Y)
        coordinates.append(Z)
    return coordinates

def robot_poses_to_joints(poses, dh_params, joint_limits):
    """
    Convert a list of robot poses in the world coordinate system to a list of robot joints in the world coordinate system.

    Parameters:
    poses (list of lists): List of robot poses in the world coordinate system [X, Y, Z, yaw, pitch, roll].
    dh_params (dict): Dictionary of DH parameters.
    joint_limits (numpy.ndarray): Array of joint limits.

    Returns:
    list of lists: List of robot joints in the world coordinate system [j1, j2, j3, j4, j5, j6].
    """
    joints = []
    q0 = np.array([0, -90, 90, 0, 90, 0]) # home position
    i = 1
    for pose in poses:
        print("Calculating pose III: ", i)
        i += 1
        pose = np.array(pose)
        q = ik.inverse_kinematics(pose, dh_params, joint_limits)

        if np.isnan(q).any() or len(q) == 0: # robot cannot reach this position
            print("ERROR: robot cannot reach pose")
            break
        if len(q) > 1:

            best_q = None
            best_score = np.inf
            for q_i in q:
                if np.abs(q_i[4]) < 1e-3: # if the fifth joint is close to zero, it means that the robot is in a singular configuration
                    continue
                score = np.power((q0-q_i), 2).sum() # sum of squared differences, it finds closest joint configuration to the previous one
                if score < best_score:
                    best_score = score
                    best_q = q_i
            joints.append(best_q)
            # if best_q is None: # Robot cannot reach this position
                # break
            q0 = best_q
        
        else:
            if np.abs(q[0,4]) < 1e-3: # if the fifth joint is close to zero, it means that the robot is in a singular configuration
                continue
            joints.append(q[0])
            q0 = q[0]
    return joints

def add_orientation_to_path(path, pcd):
    """
    Adds orientation to each point in the given path based on the closest point's normal vector in the point cloud.

    Args:
        path (numpy.ndarray): Array of 3D points representing the path.
        pcd (open3d.geometry.PointCloud): Point cloud object.

    Returns:
        numpy.ndarray: Array of transformation matrices representing the path with orientations.
    """
    
    pcd_tree = o3d.geometry.KDTreeFlann(pcd) # Create a KDTree from the point cloud
    path_with_orientations = np.zeros((path.shape[0], 4,4)) # array of transformation matrices
    point_array = np.asarray(pcd.points)

    for index, p in enumerate(path):
        # get the normal vector of the point
        [k, idx, _] = pcd_tree.search_knn_vector_3d(p, 1) # Find the closest point in the KD-tree to the target point
        # closest_point = np.asarray(pcd.points)[idx[0]] # Retrieve the closest point
        n = pcd.normals[idx[0]]
        n = n / np.linalg.norm(n)  
        x = np.array([-1, 0, 0]) # project vector [-1 0 0 ] to a plane that is defined by a normal vector
        x_proj = x - np.dot(x, n) * n
        x_proj = x_proj / np.linalg.norm(x_proj)
        
        y = np.cross(n, x_proj)
        y = y / np.linalg.norm(y)

        path_with_orientations[index, :3, :3] = np.column_stack((x_proj, y, n)) # this creates a rotation matrix from the x, y and n vectors
        path_with_orientations[index, :3, 3] = p
        path_with_orientations[index, 3, 3] = 1
    return path_with_orientations

def zig_zag_pattern(pcd, radius=65):
    """
    Generates a zig-zag pattern from a given point cloud.

    Args:
        pcd: The input point cloud.
        radius: The radius used for voxel downsampling. Default is 65.

    Returns:
        The zig-zag pattern with orientations added.

    """
    tolerance_x = radius * 0.35 # 0.25  # tolerance for the x coordinate
    voxel_radius = radius * 0.2 # 0.45
    pcd_voxel_grid = pcd.voxel_down_sample(voxel_radius)  # create sort of a grid from the point cloud, voxel represents a 3D pixel
    # o3d.visualization.draw_geometries([pcd_voxel_grid])

    points = np.asarray(pcd_voxel_grid.points)

    path = []      
    going_right = True
    while points.size != 0:
        min_x_point = points[np.argmin(points[:, 0])]  # get the most down point

        # Sort points into rows based on X-coordinate.
        stripe_idx = np.where(np.abs(points[:, 0] - min_x_point[0]) < tolerance_x)
        stripe = points[stripe_idx]
        points = np.delete(points, stripe_idx, axis=0)

        stripe_average_x = np.mean(stripe[:, 0])
        stripe[:, 0] = stripe_average_x

        stripe = stripe[stripe[:, 1].argsort()]
        
        # Remove points that are too close to each other, some are overlapping
        to_delete = []
        i = 0
        while i < len(stripe) - 1:
            for j in range(i + 1, len(stripe)):
                if np.linalg.norm(stripe[i] - stripe[j]) < voxel_radius/2:
                    to_delete.append(j)
                    continue
                i = j
                break
            i = j
        stripe = np.delete(stripe, to_delete, axis=0)

        if not going_right:  # if the stripe is going left, reverse the order of the points to create a zig-zag pattern
            stripe = np.flip(stripe, axis=0)
        going_right = not going_right
        path.extend(stripe.tolist())
    path = np.asarray(path)

    # add to the path their orientations
    return add_orientation_to_path(path, pcd)

def distance_point_to_segment(p, p1, p2):
    # Line segment vector
    v = p2 - p1
    # Point vector relative to p1
    w = p - p1
    # Projection of w onto v
    c1 = np.dot(w, v)
    if c1 <= 0:
        return np.linalg.norm(p - p1)
    c2 = np.dot(v, v)
    if c2 <= c1:
        return np.linalg.norm(p - p2)
    b = c1 / c2
    pb = p1 + b * v
    return np.linalg.norm(p - pb)

def is_point_near_hull(point, hull_vertices, tolerance):
    for i in range(len(hull_vertices)):
        p1 = hull_vertices[i]
        p2 = hull_vertices[(i + 1) % len(hull_vertices)]
        if distance_point_to_segment(point, p1, p2) <= tolerance:
            return True
    return False

def spiral_pattern(pcd, radius = 65):
    """
    This function performs a spiral pattern generation on a point cloud.
    
    Parameters:
    pcd (open3d.geometry.PointCloud): The input point cloud.
    radius (float): The radius of the spiral pattern.
    tolerance (float): The tolerance for the x coordinate.
    
    Returns:
    numpy.ndarray: The generated path with orientations represented by a series of transformation matrices.
    """


    tolerance = radius * 0.25 # tolerance for the x coordinate; 0.2 
    voxel_radius = radius * 1 # 0.6
    pcd_voxel_grid = pcd.voxel_down_sample(voxel_radius) # create sort of a grid from the point cloud, voxel represents a 3D pixel  
    # o3d.visualization.draw_geometries([pcd_voxel_grid])

    points = np.asarray(pcd_voxel_grid.points)
    points_xy = np.asarray(pcd_voxel_grid.points)[:, :2]

    path = []

    while len(points) >= 3:
        hull = ConvexHull(points_xy)
        boundary_points = points_xy[hull.vertices]
        layer_points = points[hull.vertices]
        points_xy = np.delete(points_xy, hull.vertices, axis=0)
        points = np.delete(points, hull.vertices, axis=0)

        for idx, point_xy in enumerate(points_xy):
            if is_point_near_hull(point_xy, boundary_points, tolerance):
                layer_points = np.vstack((layer_points, points[idx]))

        dtype = [('x', int), ('y', int), ('z', int)]
        struct_array1 = np.array([tuple(row) for row in layer_points], dtype=dtype)
        struct_array2 = np.array([tuple(row) for row in points], dtype=dtype)
        # We use np.in1d which checks each element in the flattened array. Then, we reshape to get the original shape back.
        # Note: This works directly on the structured array without additional manipulation
        to_keep = ~np.in1d(struct_array2, struct_array1)

        points = points[to_keep]
        points_xy = points_xy[to_keep]

        # now points in the layer sort clockwise and add them to the path 
        layer_points_xy = layer_points[:, :2]

        center_xy = np.mean(layer_points_xy, axis=0)

        angles_xy = np.array([np.arctan2(point[1] - center_xy[1], point[0] - center_xy[0]) for point in layer_points_xy]) # calculate the angle of each point relative to the center on the XY plane

        sorted_indices_xy = np.argsort(angles_xy)
        layer_points = layer_points[sorted_indices_xy]

        path.extend(layer_points.tolist())

    path = np.asarray(path)
    # add to the path their orientations 
    return add_orientation_to_path(path, pcd)

def erase_outermost_points(pcd, tolerance=20):
    points_xy = np.asarray(pcd.points)[:, :2]
    filtered_points = np.asarray(pcd.points)
    colors = np.asarray(pcd.colors)
    mask_idx = []

    hull = ConvexHull(points_xy)
    boundary_points = points_xy[hull.vertices]

    for idx, point_xy in enumerate(points_xy):
        if is_point_near_hull(point_xy, boundary_points, tolerance):
            # filtered_points = np.delete(filtered_points, idx, axis=0)
            mask_idx.append(idx)
    filtered_points = np.delete(filtered_points, mask_idx, axis=0)
    colors = np.delete(colors, mask_idx, axis=0)

    pcd.points = o3d.utility.Vector3dVector(filtered_points)
    pcd.colors = o3d.utility.Vector3dVector(colors)
    return pcd

if __name__ == "__main__":
    pcd = o3d.io.read_point_cloud("pcd.ply")
    # o3d.visualization.draw_geometries([pcd], point_show_normal=False)

    # zig_zag_pattern(pcd)

    spiral_pattern(pcd)

    # points = np.asarray(pcd.points)
    # points[:, 2] = 0
    # pcd.points = o3d.utility.Vector3dVector(points)
    # o3d.visualization.draw_geometries([pcd], point_show_normal=False)

    # # o3d.display_inlier_outlier(voxel_down_pcd, ind)
    
    # radius = 65
    # voxel_radius = radius * 0.5
    # # voxel_grid = pcd.voxel_down_sample(voxel_radius)

    # voxel_grid = o3d.geometry.VoxelGrid.create_from_point_cloud(pcd, voxel_size=voxel_radius)
    # o3d.visualization.draw_geometries([voxel_grid])
    # o3d.visualization.draw_geometries([pcd], point_show_normal=False)