package code;

import java.awt.Color;
import java.util.*;

import given.Image;
import given.Image.PixelCoordinate;

public class ImageSegmenter {

  private static Color[] colors = { Color.BLACK, Color.BLUE, Color.GREEN, Color.GRAY, Color.MAGENTA, Color.RED,
      Color.CYAN, Color.LIGHT_GRAY, Color.ORANGE, Color.PINK, Color.YELLOW, Color.DARK_GRAY };

  public Image segmentImage(Image input, double epsilon) {
    Image output = new Image(input.getHeight(), input.getWidth());
    boolean[][] visited = new boolean[input.getHeight()][input.getWidth()];
    int colorIndex = 0;

    for (int r = 0; r < input.getHeight(); ++r) {
      for (int c = 0; c < input.getWidth(); ++c) {
        if (!visited[r][c]) {
          colorConnectedComponent(input, output, new PixelCoordinate(r, c), visited, epsilon,
              colors[colorIndex % colors.length]);
          colorIndex++;
        }
      }
    }
    return output;
  }

  private void colorConnectedComponent(Image input, Image output, PixelCoordinate start, boolean[][] visited,
      double epsilon, Color color) {
    Queue<PixelCoordinate> queue = new LinkedList<>();
    queue.add(start);

    while (!queue.isEmpty()) {
      PixelCoordinate current = queue.poll();
      if (withinBounds(current, input) && !visited[current.r][current.c]
          && Math.abs(input.getPixelVal(current) - input.getPixelVal(start)) <= epsilon) {
        visited[current.r][current.c] = true;
        output.setColor(current, color);

        queue.addAll(getNeighbors(current));
      }
    }
  }

  private boolean withinBounds(PixelCoordinate pc, Image img) {
    return pc.r >= 0 && pc.r < img.getHeight() && pc.c >= 0 && pc.c < img.getWidth();
  }

  private List<PixelCoordinate> getNeighbors(PixelCoordinate pc) {
    return Arrays.asList(
        new PixelCoordinate(pc.r - 1, pc.c),
        new PixelCoordinate(pc.r + 1, pc.c),
        new PixelCoordinate(pc.r, pc.c - 1),
        new PixelCoordinate(pc.r, pc.c + 1));
  }

  public Image dummyIteration(Image input) {
    int colorIndex = 0;
    Image output = new Image(input.getHeight(), input.getWidth());

    for (int r = 0; r < input.getHeight(); ++r) {
      for (int c = 0; c < input.getWidth(); ++c) {
        Color nextColorToUse = colors[colorIndex % colors.length];
        output.setColor(new PixelCoordinate(r, c), nextColorToUse);
        colorIndex++;
      }
    }
    return output;
  }
}
