import open3d as o3d
from PIL import Image
import numpy as np

def create_pc():
    """
    Create a point cloud from grayscale and depth images.

    Returns:
        pcd_scaled (open3d.geometry.PointCloud): The scaled and transformed point cloud.
    """
    img_gray = Image.open("GRAYSCALE.png")
    img_depth = Image.open("GRAYSCALE.png")

    # Convert images to numpy arrays
    gray_frame = np.array(img_gray, dtype=np.uint8).reshape(2048,2048) / 255 # Grayscale value scaled from 0-255 to 0-1
    depth_frame = np.array(img_depth, dtype=np.uint16).reshape(2048,2048) * 0.04 # Height value
    # X,Y scale - 0.0616mm/pixel, Z scale - 0.04mm/pixel

    gray_frame = gray_frame.T
    depth_frame = depth_frame.T

    # Create base pointcloud
    point_cloud = o3d.geometry.PointCloud()
    x = np.linspace(0, 2048, 2048) 
    mesh_x, mesh_y = np.meshgrid(x, x)

    # Put depth data into pointcloud
    xyz = np.zeros((np.size(mesh_x), 3))
    xyz[:, 0] = np.reshape(mesh_x, -1)
    xyz[:, 1] = np.reshape(mesh_y, -1)
    xyz[:, 2] = np.reshape(depth_frame, -1)
    idx = np.argwhere(xyz[:, 2] == 0) # Get indexes of all 0 heights (this means the depth was not detected)
    xyz = np.delete(xyz, idx, axis=0) # Delete all points with 0 heights
    point_cloud.points = o3d.utility.Vector3dVector(xyz)

    # Put color data into pointcloud
    colors = np.zeros((np.size(mesh_x), 3))
    colors[:, 0] = np.reshape(gray_frame, -1)
    colors[:, 1] = np.reshape(gray_frame, -1)
    colors[:, 2] = np.reshape(gray_frame, -1)
    colors = np.delete(colors, idx, axis=0) # Delete all points with 0 heights
    point_cloud.colors = o3d.utility.Vector3dVector(colors)

    # Scale the x and y coordinates
    scale_x = 0.616
    scale_y = 0.616

    points_scaled = np.array(point_cloud.points)
    points_scaled[:, 0] *= scale_x  # scale x coordinates
    points_scaled[:, 1] *= scale_y  # scale y coordinates

    pcd_scaled = o3d.geometry.PointCloud()
    pcd_scaled.points = o3d.utility.Vector3dVector(points_scaled)
    pcd_scaled.colors = point_cloud.colors

    new_points = np.asarray(pcd_scaled.points) 
    new_points -= np.array([630, 630, 680]) # Move the point cloud to the origin if the camera csys; RB-1200 workspace: 1260x1260x1000
    # XXX To Z nesedi, melo by to byt 500, predesle 664
    pcd_scaled.points = o3d.utility.Vector3dVector(new_points)

    # o3d.io.write_point_cloud("pcd_scaled.ply", pcd_scaled)
    # o3d.visualization.draw_geometries([pcd_scaled])

    return pcd_scaled

def erase_floor(pcd, z_threshold=350):
    """
    Erases the points below a given z-threshold from a point cloud.

    Parameters:
    pcd (open3d.geometry.PointCloud): The input point cloud.
    z_threshold (float): The z-coordinate threshold. Points below this threshold will be removed.

    Returns:
    open3d.geometry.PointCloud: The updated point cloud with points below the z-threshold removed.
    """

    points = np.asarray(pcd.points)
    colors = np.asarray(pcd.colors)

    # Identify the points which are above the z-threshold
    mask = points[:, 2] > z_threshold

    # Filter the points and colors
    filtered_points = points[mask]
    filtered_colors = colors[mask]

    # Update the point cloud data
    pcd.points = o3d.utility.Vector3dVector(filtered_points)
    pcd.colors = o3d.utility.Vector3dVector(filtered_colors)
    return pcd

def create_downwards_normals(pcd):
    # Compute normals, all must be pointing downwards, inside the object
    pcd.estimate_normals()
    pcd.orient_normals_towards_camera_location(camera_location=np.array([1300, 200, -1000]))
    return pcd

if __name__ == "__main__":
    create_pc()