import numpy as np
import argparse
import matplotlib.pyplot as plt

# --- Generator functions (unchanged) ---
def generate_horizontal_segments(min_x, max_x, min_y, max_y, num_points=500, base_offset_factor=0.2, segment_length_factor=0.15, frequency=10, direction='out'):
    width = max_x - min_x
    height = max_y - min_y
    center_x = (min_x + max_x) / 2

    base_offset = width * base_offset_factor
    segment_length = width * segment_length_factor

    base_x_left = center_x - base_offset
    base_x_right = center_x + base_offset

    y_levels = np.linspace(min_y, max_y, frequency)
    points_per_single_segment = max(2, num_points // (frequency * 2))

    all_points_left = []
    all_points_right = []

    for y_level in y_levels:
        # Left side
        if direction == 'out': 
            x_start_left = base_x_left
            x_end_left = base_x_left - segment_length
        elif direction == 'in': 
            x_start_left = base_x_left - segment_length
            x_end_left = base_x_left
        else:
            raise ValueError("Direction must be 'in' or 'out'")

        x_segment_left = np.linspace(x_start_left, x_end_left, points_per_single_segment)
        y_segment_left = np.full(x_segment_left.shape, y_level)
        x_segment_left = np.clip(x_segment_left, min_x, max_x)
        all_points_left.append(np.vstack((x_segment_left, y_segment_left)).T)

        # Right side
        if direction == 'out': 
            x_start_right = base_x_right
            x_end_right = base_x_right + segment_length
        elif direction == 'in': 
            x_start_right = base_x_right + segment_length
            x_end_right = base_x_right
        else:
            raise ValueError("Direction must be 'in' or 'out'")

        x_segment_right = np.linspace(x_start_right, x_end_right, points_per_single_segment)
        y_segment_right = np.full(x_segment_right.shape, y_level)
        x_segment_right = np.clip(x_segment_right, min_x, max_x)
        all_points_right.append(np.vstack((x_segment_right, y_segment_right)).T)

    final_points_left = np.concatenate(all_points_left, axis=0) if all_points_left else np.empty((0, 2))
    final_points_right = np.concatenate(all_points_right, axis=0) if all_points_right else np.empty((0, 2))
    return final_points_left, final_points_right

def generate_circles(min_x, max_x, min_y, max_y, num_points=1000, base_offset_factor=0.2, num_circles=5, points_per_circle=None):
    width = max_x - min_x
    height = max_y - min_y
    center_x = (min_x + max_x) / 2
    base_offset = width * base_offset_factor
    radius = min(height / (num_circles * 2.1), abs(base_offset * 0.9))
    if radius <= 0:
        return np.empty((0, 2)), np.empty((0, 2))
    cx_left = center_x - base_offset
    cx_right = center_x + base_offset
    y_centers = np.linspace(min_y + radius, max_y - radius, num_circles)
    if points_per_circle is None:
        points_per_circle = max(10, num_points // (num_circles * 2))
    theta = np.linspace(0, 2 * np.pi, points_per_circle, endpoint=False)
    all_points_left, all_points_right = [], []
    for i, cy in enumerate(y_centers):
        x_circ_left = cx_left + radius * np.cos(theta)
        y_circ_left = cy + radius * np.sin(theta)
        all_points_left.append(np.vstack((x_circ_left, y_circ_left)).T)
        x_circ_right = cx_right + radius * np.cos(theta)
        y_circ_right = cy + radius * np.sin(theta)
        all_points_right.append(np.vstack((x_circ_right, y_circ_right)).T)
        if i < num_circles - 1:
            points_per_connection = max(2, points_per_circle // 4)
            y_connect_left = np.linspace(cy + radius, y_centers[i+1] - radius, points_per_connection)
            x_connect_left = np.full(y_connect_left.shape, cx_left)
            all_points_left.append(np.vstack((x_connect_left, y_connect_left)).T)
            y_connect_right = np.linspace(cy + radius, y_centers[i+1] - radius, points_per_connection)
            x_connect_right = np.full(y_connect_right.shape, cx_right)
            all_points_right.append(np.vstack((x_connect_right, y_connect_right)).T)
    final_points_left = np.concatenate(all_points_left, axis=0) if all_points_left else np.empty((0, 2))
    final_points_right = np.concatenate(all_points_right, axis=0) if all_points_right else np.empty((0, 2))
    return final_points_left, final_points_right

def generate_rectangles(min_x, max_x, min_y, max_y, num_points=300, offset_factor=0.1, rect_width_factor=0.15):
    width = max_x - min_x
    height = max_y - min_y
    center_x = (min_x + max_x) / 2
    offset = width * offset_factor
    rect_width = width * rect_width_factor
    x_left_inner = center_x - offset
    x_left_outer = x_left_inner - rect_width
    x_right_inner = center_x + offset
    x_right_outer = x_right_inner + rect_width
    x_left_inner, x_left_outer = np.clip([x_left_inner, x_left_outer], min_x, max_x)
    x_right_inner, x_right_outer = np.clip([x_right_inner, x_right_outer], min_x, max_x)
    points_per_edge = max(2, num_points // 8)
    x_bottom_L = np.linspace(x_left_outer, x_left_inner, points_per_edge)
    y_bottom_L = np.full(x_bottom_L.shape, min_y)
    y_inner_L = np.linspace(min_y, max_y, points_per_edge)
    x_inner_L = np.full(y_inner_L.shape, x_left_inner)
    x_top_L = np.linspace(x_left_inner, x_left_outer, points_per_edge)
    y_top_L = np.full(x_top_L.shape, max_y)
    y_outer_L = np.linspace(max_y, min_y, points_per_edge)
    x_outer_L = np.full(y_outer_L.shape, x_left_outer)
    points_left = np.concatenate([np.vstack((x_bottom_L, y_bottom_L)).T[:-1],
                                  np.vstack((x_inner_L, y_inner_L)).T[:-1],
                                  np.vstack((x_top_L, y_top_L)).T[:-1],
                                  np.vstack((x_outer_L, y_outer_L)).T], axis=0)
    x_bottom_R = np.linspace(x_right_inner, x_right_outer, points_per_edge)
    y_bottom_R = np.full(x_bottom_R.shape, min_y)
    y_outer_R = np.linspace(min_y, max_y, points_per_edge)
    x_outer_R = np.full(y_outer_R.shape, x_right_outer)
    x_top_R = np.linspace(x_right_outer, x_right_inner, points_per_edge)
    y_top_R = np.full(x_top_R.shape, max_y)
    y_inner_R = np.linspace(max_y, min_y, points_per_edge)
    x_inner_R = np.full(y_inner_R.shape, x_right_inner)
    points_right = np.concatenate([np.vstack((x_bottom_R, y_bottom_R)).T[:-1],
                                   np.vstack((x_outer_R, y_outer_R)).T[:-1],
                                   np.vstack((x_top_R, y_top_R)).T[:-1],
                                   np.vstack((x_inner_R, y_inner_R)).T], axis=0)
    return points_left, points_right

def generate_linear_zigzag(min_x, max_x, min_y, max_y, num_points=500, base_offset_factor=0.2, amplitude_factor=0.15, frequency=5):
    width = max_x - min_x
    height = max_y - min_y
    center_x = (min_x + max_x) / 2
    base_offset = width * base_offset_factor
    amplitude = width * amplitude_factor
    base_x_left = center_x - base_offset
    base_x_right = center_x + base_offset
    points_needed_min = (int(frequency * 2) + 1) * 2
    points_per_path = max(points_needed_min, num_points // 2)
    y_up = np.linspace(min_y, max_y, points_per_path // 2)
    y_down = np.linspace(max_y, min_y, points_per_path // 2)
    y_path = np.concatenate((y_up, y_down))
    norm_y = (y_path - min_y) / height
    phase = (frequency * norm_y) % 1.0
    tri_wave = np.where(phase < 0.5, (phase * 4) - 1, ((1.0 - phase) * 4) - 1)
    x_oscillation = amplitude * tri_wave
    x_left = base_x_left - x_oscillation
    x_right = base_x_right + x_oscillation
    x_left = np.clip(x_left, min_x, max_x)
    x_right = np.clip(x_right, min_x, max_x)
    points_left = np.vstack((x_left, y_path)).T
    points_right = np.vstack((x_right, y_path)).T
    return points_left, points_right

# --- Wrapper Functions ---
def generate_gegenlauf(min_x, max_x, min_y, max_y, num_points=500, frequency=5):
    return generate_linear_zigzag(min_x, max_x, min_y, max_y, num_points=num_points,
                                  base_offset_factor=0.25, amplitude_factor=0.15, frequency=frequency)

def generate_kreisend(min_x, max_x, min_y, max_y, num_points=1000, num_circles=5):
    return generate_circles(min_x, max_x, min_y, max_y, num_points=num_points,
                           base_offset_factor=0.25, num_circles=num_circles)

def generate_lateral(min_x, max_x, min_y, max_y, num_points=500, frequency=10):
    return generate_horizontal_segments(min_x, max_x, min_y, max_y, num_points=num_points,
                                        base_offset_factor=0.3,
                                        segment_length_factor=0.15,
                                        frequency=frequency,
                                        direction='out')

def generate_medial(min_x, max_x, min_y, max_y, num_points=500, frequency=10):
    return generate_horizontal_segments(min_x, max_x, min_y, max_y, num_points=num_points,
                                        base_offset_factor=0.15,
                                        segment_length_factor=0.15,
                                        frequency=frequency,
                                        direction='in')

def generate_paravertebral(min_x, max_x, min_y, max_y, num_points=300):
    return generate_rectangles(min_x, max_x, min_y, max_y, num_points=num_points,
                               offset_factor=0.1, rect_width_factor=0.15)

# --- Helper: Write output files ---
def write_dat_file(filename, points):
    """
    Writes a .dat file with each line as:
    X Y Z A B C
    Z, A, B, C are set to 0.
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
        for i, move in enumerate(points, start=1):
            X, Y = move
            f.write("DECL E6POS X%d={X %.3f,Y %.3f,Z %.3f,A %.3f,B %.3f,C %.3f,S 2,T 89}\n" %
                    (i, X, Y, 0, 0, 0, 0))
            f.write("DECL FDAT FX%d={TOOL_NO 0,BASE_NO 1,IPO_FRAME #BASE,POINT2[] " "}\n" % (i))
            f.write("DECL PDAT PPX%d={VEL 100,ACC 100,APO_DIST 5,APO_MODE #CDIS,GEAR_JERK 100}\n" % (i))
        f.write("ENDDAT\n")

def write_src_file(filename, dat_filename, points):
    """
    Writes a .src file with a header and each point line formatted as:
    MOVE x y 0 0 0 0
    """
    with open(filename, "w") as f:
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
        for i in range(1, len(points) + 1):
            f.write("PTP X%d\n" % i)
        f.write("END\n")

# --- Main execution ---
def main():
    parser = argparse.ArgumentParser(description="Generate massage path files (.dat and .src) based on pattern type.")
    parser.add_argument("pattern", type=str,
                        help="Type of path: gegenlauf, kreisend, lateral, medial, or paravertebral")
    parser.add_argument("output_prefix", type=str,
                        help="Output filename prefix (files will be <prefix>.dat and <prefix>.src)")
    args = parser.parse_args()

    # Set working bounds and parameters (adjust these as needed)
    MIN_X = -200
    MAX_X = 200
    MIN_Y = 0
    MAX_Y = 400

    # Map the pattern type to a generator
    pattern = args.pattern.lower()
    if pattern == "gegenlauf":
        pts_left, pts_right = generate_gegenlauf(MIN_X, MAX_X, MIN_Y, MAX_Y, num_points=100, frequency=6)
    elif pattern == "kreisend":
        pts_left, pts_right = generate_kreisend(MIN_X, MAX_X, MIN_Y, MAX_Y, num_points=100, num_circles=6)
    elif pattern == "lateral":
        pts_left, pts_right = generate_lateral(MIN_X, MAX_X, MIN_Y, MAX_Y, num_points=100, frequency=6)
    elif pattern == "medial":
        pts_left, pts_right = generate_medial(MIN_X, MAX_X, MIN_Y, MAX_Y, num_points=100, frequency=6)
    elif pattern == "paravertebral":
        pts_left, pts_right = generate_paravertebral(MIN_X, MAX_X, MIN_Y, MAX_Y, num_points=100)
    else:
        raise ValueError("Unknown pattern type. Please use: gegenlauf, kreisend, lateral, medial, or paravertebral.")

    # Combine the left and right paths
    points = np.concatenate((pts_left, pts_right), axis=0)

    # --- Plotting the generated path ---
    plt.figure(figsize=(8, 6))
    plt.scatter(points[:, 0], points[:, 1], s=5, alpha=0.8)
    plt.title(f"Generated Massage Pattern: {pattern.capitalize()}")
    plt.xlabel("X-coordinate")
    plt.ylabel("Y-coordinate")
    plt.grid(True, linestyle='--', alpha=0.6)
    plt.axis('equal')
    plt.show()

    # Write output files
    dat_filename = args.output_prefix + ".dat"
    src_filename = args.output_prefix + ".src"
    write_dat_file(dat_filename, points)
    write_src_file(src_filename, dat_filename, points)
    print(f"Files '{dat_filename}' and '{src_filename}' generated with {len(points)} points.")

if __name__ == "__main__":
    main()
