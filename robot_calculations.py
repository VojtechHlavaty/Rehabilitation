import numpy as np
from scipy.optimize import minimize
from scipy.optimize import Bounds

# [theta, d, a, alpha] in degrees for alpha
dh_parameters = [
    [0, 0, 0, 0],
    [0, 0, 0, 90],
    [0, 0, 0.42, -90],
    [0, 0, 0, -90],
    [0, 0, 0.40, 90],
    [0, 0, 0, 90],
    [0, 0, 0, -90],
]

joint_limits = [
    (np.deg2rad(-170), np.deg2rad(170)),  # Joint 1
    (np.deg2rad(-120), np.deg2rad(120)),  # Joint 2
    (np.deg2rad(-170), np.deg2rad(170)),  # Joint 3
    (np.deg2rad(-120), np.deg2rad(120)),  # Joint 4
    (np.deg2rad(-170), np.deg2rad(170)),  # Joint 5
    (np.deg2rad(-120), np.deg2rad(120)),  # Joint 6
    (np.deg2rad(-175), np.deg2rad(175))   # Joint 7
]

def dh_transform(theta, d, a, alpha):
    alpha = np.deg2rad(alpha)  # Convert alpha to radians
    return np.array([
        [np.cos(theta), -np.sin(theta) * np.cos(alpha),  np.sin(theta) * np.sin(alpha), a * np.cos(theta)],
        [np.sin(theta),  np.cos(theta) * np.cos(alpha), -np.cos(theta) * np.sin(alpha), a * np.sin(theta)],
        [0,              np.sin(alpha),                 np.cos(alpha),                 d],
        [0,              0,                             0,                             1]
    ])

def forward_kinematics(joint_angles):
    T = np.eye(4)
    transforms = []
    for i, (theta, d, a, alpha) in enumerate(dh_parameters):
        theta += joint_angles[i]  # Joint angle contribution
        A = dh_transform(theta, d, a, alpha)
        T = T @ A
        transforms.append(T)
    return transforms

def compute_jacobian(angles):
    n = len(dh_parameters)
    joint_angles = np.array(angles)
    transforms = forward_kinematics(joint_angles)

    p_e = transforms[-1][:3, 3]  # End-effector position

    J = np.zeros((6, n))

    for i in range(n):
        if i == 0:
            z_i = np.array([0, 0, 1])
            p_i = np.array([0, 0, 0])
        else:
            z_i = transforms[i - 1][:3, 2]
            p_i = transforms[i - 1][:3, 3]

        J[:3, i] = np.cross(z_i, (p_e - p_i))
        J[3:, i] = z_i

    return J

def get_manipulatibility(joint_angles):
    J = compute_jacobian(joint_angles)
    U, s, Vh = np.linalg.svd(J)
    manipulability = np.prod(s)  # Product of singular values
    return -manipulability  # Negative for maximization with minimize()

if __name__ == "__main__":
    lower_bounds, upper_bounds = zip(*joint_limits)
    bounds = Bounds(lower_bounds, upper_bounds)
    initial_guess = np.deg2rad([-10, 20, 70, -40, 50, 140, 70])


    # Optimize to maximize the manipulability measure
    result = minimize(get_manipulatibility, initial_guess, bounds=bounds)

    optimum = result.x
    max_manipulability = -result.fun  # Negate to get the positive manipulability

    #print("Optimal Joint Angles (radians):", optimum)
    print("Optimal Joint Angles (degrees):", [0 if i < 1 else np.rad2deg(i) for i in optimum])
    print("Maximum Manipulability Ellipsoid Volume:", max_manipulability)
