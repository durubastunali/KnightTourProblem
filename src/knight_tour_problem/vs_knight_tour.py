import matplotlib
matplotlib.use('TkAgg')  # Set backend for interactive windows
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from matplotlib.widgets import Slider

# Parse moves from the file and determine the board size
def parse_moves_and_get_board_size(file_path):
    with open(file_path, 'r') as f:
        lines = f.readlines()

    moves = []
    max_x, max_y = 0, 0
    for line in lines:
        line = line.strip()
        if '-' in line and line.replace('-', '').replace(' ', '').isdigit():
            x, y = map(int, line.split('-'))
            moves.append((x-1, y-1))  # Convert 1-based coordinates to 0-based
            max_x, max_y = max(max_x, x), max(max_y, y)

    board_size = max(max_x, max_y)  # Board size is determined by the largest coordinate
    return moves, board_size

# Visualize the knight's tour with a slider for speed control
def visualize_knight_tour_with_slider(board_size, moves):
    # Dynamic size adjustment for markers and lines
    marker_size = max(1, 10 - board_size // 10)  # Marker size scales with board size
    line_width = max(0.5, 2 - board_size / 50)   # Line width scales with board size

    # Create the figure and layout
    fig = plt.figure(figsize=(board_size / 4, board_size / 4))
    grid = fig.add_gridspec(5, 4, wspace=0.4, hspace=0.8)  # Grid layout

    ax_board = fig.add_subplot(grid[:4, :4])  # Main board
    ax_legend = fig.add_subplot(grid[4, :2])  # Legend area
    ax_slider = fig.add_subplot(grid[4, 2:])  # Slider area

    # Configure the board
    ax_board.set_xlim(0, board_size)
    ax_board.set_ylim(0, board_size)
    ax_board.set_xticks(range(board_size + 1))
    ax_board.set_yticks(range(board_size + 1))
    ax_board.grid(color='black', linestyle='-', linewidth=1)
    ax_board.set_aspect('equal')

    # Create the knight's path and marker
    knight_path, = ax_board.plot([], [], 'o-', color='blue', markersize=marker_size, linewidth=line_width, label="Knight Path")
    knight_marker, = ax_board.plot([], [], 'o', color='red', markersize=marker_size, label="Knight Position")

    # Adjust coordinates to be in the center of cells
    moves = [(x + 0.5, y + 0.5) for x, y in moves]

    # Configure the legend
    ax_legend.axis('off')  # Remove axes from legend area
    ax_legend.legend(handles=[knight_path, knight_marker], labels=["Knight Path", "Knight Position"], loc="center")

    # Configure the slider
    ax_slider.axis('off')  # Remove axes from slider area
    slider_ax = plt.axes([0.25, 0.05, 0.5, 0.03], facecolor='lightgoldenrodyellow')  # Slider position
    speed_slider = Slider(slider_ax, 'Speed', 50, 1000, valinit=500, valstep=50)

    # Initialize animation speed
    anim_speed = [1000 - speed_slider.val]

    # Update animation speed when slider changes
    def update_speed(val):
        anim_speed[0] = 1000 - speed_slider.val

    speed_slider.on_changed(update_speed)

    # Update the animation frame
    def update(frame):
        x, y = zip(*moves[:frame+1])
        knight_path.set_data(x, y)
        knight_marker.set_data(x[-1:], y[-1:])
        ax_board.set_title(f"Step: {frame+1}/{len(moves)}", fontsize=12)
        return knight_path, knight_marker

    # Animate the knight's tour
    def dynamic_animation():
        frame = 0
        while frame < len(moves):
            update(frame)
            plt.pause(anim_speed[0] / 1000.0)  # Adjust pause duration based on speed
            frame += 1

    plt.suptitle("Knight's Tour Visualization", fontsize=14)
    dynamic_animation()
    plt.show(block=True)

# Main execution
file_path = '1.txt'  # File containing moves
moves, board_size = parse_moves_and_get_board_size(file_path)  # Parse moves and determine board size
visualize_knight_tour_with_slider(board_size, moves)
