import open3d as o3d
import numpy as np
import pc_processing_functions as PCF
import poses_generation_functions as PGF
from scipy.spatial.transform import Rotation
import matplotlib.pyplot as plt

def transform_matrices_to_coordinates(matrices):
    coordinates = []
    for matrix in matrices:
        X, Y, Z = matrix[0:3, 3]
        rotation_matrix = matrix[0:3, 0:3]
        # Using 'zyx' convention: yaw, pitch, roll in radians
        euler_angles = Rotation.from_matrix(rotation_matrix).as_euler('zyx', degrees=False)
        yaw, pitch, roll = euler_angles
        coordinates.append([X, Y, Z, yaw, pitch, roll])
    return coordinates

def write_dat_file(filename, moves):
    """
    Write a .dat file that contains the base definition and all positions.
    The file format is as in your Massage4.dat:
    
    &ACCESS RVP
    &REL 1
    DEF Massage4DAT( )
      BASE_DATA[1]={X 521.83,Y 481.52,Z -116.04,A -179.997,B 0,C 0}
      BASE_NAME[1,]="BASE_BED"
      BASE_TYPE[1]=#BASE
      PTP_POS[1]={X ... ,Y ... ,Z ... ,A ... ,B ... ,C ...}
      PTP_POS[2]={X ... ,Y ... ,Z ... ,A ... ,B ... ,C ...}
      ...
    END
    """
    with open(filename, "w") as f:

        f.write("DEFDAT ScanAndSand")
        f.write(";FOLD EXTERNAL DECLARATIONS;%{PE}%MKUKATPBASIS,%CEXT,%VCOMMON,%P\n")
        f.write("FOLD BASISTECH EXT;%{PE}%MKUKATPBASIS,%CEXT,%VEXT,%P\n")
        f.write("EXT  BAS (BAS_COMMAND  :IN,REAL  :IN )\n")
        f.write("DECL INT SUCCESS\n")
        f.write(";ENDFOLD (BASISTECH EXT)\n")
        f.write(";FOLD USER EXT;%{E}%MKUKATPUSER,%CEXT,%VEXT,%P\n")
        f.write(";Make your modifications here\n")
        f.write(";ENDFOLD (USER EXT)\n")
        f.write(";ENDFOLD (EXTERNAL DECLARATIONS)\n")
        # Write each generated position
        for i, move in enumerate(moves, start=1):
            X, Y, Z, yaw, pitch, roll = move
            A = np.rad2deg(yaw)
            B = np.rad2deg(pitch)
            C = np.rad2deg(roll)
            f.write("DECL E6POS X%d={X %.3f,Y %.3f,Z %.3f,A %.3f,B %.3f,C %.3f,S 2,T 89}\n" %
                    (i, X/6, Y/3, Z/6, A, B, C))
            f.write("DECL FDAT FX%d={TOOL_NO 0,BASE_NO 1,IPO_FRAME #BASE,POINT2[] " "}\n" % (i))
            f.write("DECL PDAT PPX%d={VEL 100,ACC 100,APO_DIST 5,APO_MODE #CDIS,GEAR_JERK 100}\n" % (i))
        f.write("ENDDAT\n")

def write_src_file(src_filename, dat_filename, num_moves):
    """
    Write a .src file that includes the generated .dat file.
    The .src file is generated to match the format used in Massage1.src.
    
    Parameters:
      src_filename (str): Name of the .src file to write.
      dat_filename (str): Name of the .dat file containing robot data.
      num_moves (int): Number of positions to call in the motion program.
    """
    with open(src_filename, "w") as f:
        f.write("&ACCESS RVP\n")
        f.write("&REL 1\n")
        f.write("&PARAM EDITMASK = *\n")
        f.write("&PARAM TEMPLATE = C:\\KRC\\Roboter\\Template\\vorgabe\n")
        f.write("DEF Massage4( )\n")
        f.write("  ;FOLD INI\n")
        f.write("  $VEL.CP = 0.25\n")
        f.write("  $ACC.CP = 1\n")
        f.write("  ;ENDFOLD\n")
        f.write("  BAS (#BASE,BASE_DATA)\n")
        f.write("  &INCLUDE \"" + dat_filename + "\"\n")
        f.write("\n")
        for i in range(1, num_moves + 1):
            f.write("PTP X%d\n" % i)
        f.write("END\n")



# Example usage:
if __name__ == "__main__":

   pcd = PCF.create_pc()
   pcd = pcd.voxel_down_sample(voxel_size=10) # 20mmm

   # R = Rotation.from_euler('z', -90, degrees=True).as_matrix()
   # pcd = pcd.rotate(R, center=(0, 0, 0))
   # pcd = pcd.transform(T)
   # pcd = PCF.erase_floor(pcd, z_threshold = -50) # predesle -300, -525
   

   # _, ind = pcd.remove_statistical_outlier(nb_neighbors=10, std_ratio=2.0)
   # pcd = pcd.select_by_index(ind)
   # pcd = PGF.erase_outermost_points(pcd, 25)

   pcd = PCF.create_downwards_normals(pcd)

   # o3d.visualization.draw_geometries([pcd], point_show_normal=True)

   # radii = np.linspace(.01, 10, 100) # [6, 8, 10, 14, 18, 22] # There should be more radii in list, but it works with only one
   # mesh = o3d.geometry.TriangleMesh.create_from_point_cloud_ball_pivoting(pcd, o3d.utility.DoubleVector(radii))
   # o3d.io.write_triangle_mesh("output_mesh.stl", mesh)

   # Crate Zig-Zag pattern
   # zig_zag_path = PGF.zig_zag_pattern(pcd, 120 )
   spiral_path = PGF.spiral_pattern(pcd, 250)

   moves = transform_matrices_to_coordinates(spiral_path)
   
   # Optionally, convert all numpy floats to native Python floats:
   moves_native = [[float(i) for i in move] for move in moves]
   
   # Write the .src file (KUKA program)
   write_dat_file("Massage4.dat", moves_native)
   # Write the .src file that calls the positions from the .dat file
#    write_src_file("Massage4.src", "Massage4.dat", len(moves_native))

   # PGF.plot_coordinate_systems_matplotlib(zig_zag_path)
   # PGF.plot_coordinate_systems_matplotlib(spiral_path)
   # zig_zag_poses = PGF.transform_matrices_to_coordinates(zig_zag_path) # list of robot poses in the world csys (x, y, z, yaw, pitch, roll)
   # spiral_poses = PGF.transform_matrices_to_coordinates(spiral_path)
   # print(zig_zag_poses)
   # print(spiral_poses)
   # robot_points = PGF.transform_matrices_to_points(zig_zag_path)

   # for pose in zig_zag_poses:
      # print(f'X: {pose[0]}, Y: {pose[1]}, Z: {pose[2]}, Yaw: {np.rad2deg(pose[3])}, Pitch: {np.rad2deg(pose[4])}, Roll: {np.rad2deg(pose[5])}')
      # print(f'X: {pose[0]}, Y: {pose[1]}, Z: {pose[2]}, Yaw: {pose[3]}, Pitch: {pose[4]}, Roll: {pose[5]}')

   # PGF.robot_post_processor_points(robot_poses)
   # zig_zag_joints = PGF.robot_poses_to_joints(zig_zag_poses, dh_params, joint_limits) # list of robot joints in the world csys (j1, j2, j3, j4, j5, j6)
   # spiral_joints = PGF.robot_poses_to_joints(spiral_poses, dh_params, joint_limits)

   # print(zig_zag_poses)
   # print(zig_zag_joints)
   
   # PGF.robot_post_processor_joints(robot_joints)
   # PF.robot_post_processor(zig_zag_joints, dh_params)
   # PF.robot_post_processor_points(zig_zag_poses, dh_params, joint_limits)
   # PF.robot_post_processor_points(zig_zag_poses, dh_params, joint_limits)