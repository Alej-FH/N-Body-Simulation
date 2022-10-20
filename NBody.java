public class NBody {
    public static void main(String[] args) {
        // Parse command-line arguments.
        double tot = Double.parseDouble(args[0]);
        double dT = Double.parseDouble(args[1]);
        StdDraw.enableDoubleBuffering();

        // Read universe from standard input.
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();

        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] mass = new double[n];
        String[] image = new String[n];

        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }

        // Initialize standard drawing.
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);

        // Play music on standard audio.
        StdAudio.play("2001.wav");

        // Simulate the universe.
        for (double t = 0; t < tot; t += dT) {
            // Calculate net forces.
            double[] fx = new double[n];
            double[] fy = new double[n];

            // compares two objects to one another
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) continue;

                    double dX = px[j] - px[i];
                    double dY = py[j] - py[i];
                    double r = Math.sqrt(dX * dX + dY * dY);

                    double force = (6.67e-11 * mass[i] * mass[j]) / (r * r);

                    //
                    fx[i] += force * (dX / r);
                    fy[i] += force * (dY / r);

                }
            }
            // Update velocities and positions.
            for (int i = 0; i < n; i++) {
                double ax = fx[i] / mass[i];
                double ay = fy[i] / mass[i];

                vx[i] = vx[i] + ax * dT;
                vy[i] = vy[i] + ay * dT;
                px[i] = px[i] + vx[i] * dT;
                py[i] = py[i] + vy[i] * dT;

            }
            // Draw universe to standard drawing.
            StdDraw.picture(0, 0, "starfield.jpg");
            for (int i = 0; i < n; i++) {
                StdDraw.picture(px[i], py[i], image[i]);

            }
            StdDraw.show();
            StdDraw.pause(20);

        }
        // Print universe to standard output.
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}
