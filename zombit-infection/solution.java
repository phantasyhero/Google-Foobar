/**
 *  Note: Google makes a claim that x = column, y = row. But this results in a confusing
 *  accessor for population, requiring population[y][x] rather than population[x][y]
 *
 *  Solution on this page is pretty much guaranteed to be inefficient, but it works.
 *
 *
 *  These answers are intended to be an open topic for discussion and not to be used
 *  to complete the challenges itself. If you copy my code to use within Foobar, you
 *  are a cheater! :)
 */
public static int[][] answer(int[][] population, int x, int y, int strength) { 

        // Your code goes here.
        // Check to see if initial infection even happens, if not return population
        if (strength < population[y][x])
            return population;
            
        population[y][x] = -1;

        // Main iteration loop
        while (true) {
            // Deep copy as Java passes by reference and will result in incorrect answers if we don't do this.
        	int[][] population_pass = array_deep_copy(population);

            // Run an infection cycle over the population array as it is currently
        	int[][] infection_cycle = run_infection_pass(population, strength);

            // If our array is unchanged from the previous iteration we are done.
            if (arrays_equal(population_pass, infection_cycle))
                return infection_cycle;
            
            // Assign population to the new infection cycle
            population = infection_cycle;
        }
    }
    
    private static boolean arrays_equal(int[][] base, int[][] comparison) {
        // We're never going to have arrays of non-equal dimensions
        // Just a basic comparison between two 2D arrays provided they are equal dimensions
        for (int i = 0; i < base.length; i++) {
            for (int j = 0; j < base[i].length; j++) {
                if (base[i][j] != comparison[i][j])
                    return false;
            }
        }
        return true;
    }
    
    private static int[][] array_deep_copy(int[][] original) {
        // Simple deep copy function for 2D arrays
        int[][] holder = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            holder[i] = new int[original[i].length];
            for (int j = 0; j < original[i].length; j++) {
                holder[i][j] = original[i][j];
            }
        }
        return holder;
    }
    
    private static int[][] run_infection_pass(int[][] population, int strength) {
        // Main infection pass loop. Broken up for easier reading.
        for (int col = 0; col < population.length; col++) {
            for (int row = 0; row < population[col].length; row++) {
                if (population[col][row] == -1) {
                    // infect neighbors
                    int previous_col = col - 1;
                    int next_col = col + 1;
                    int previous_row = row - 1;
                    int next_row = row + 1;
                    
                    // Verify all cases for index out of bounds exceptions
                    if (previous_col >= 0) {
                        if (population[previous_col][row] <= strength)
                            population[previous_col][row] = -1;
                    }
                    if (next_col < population.length) {
                        if (population[next_col][row] <= strength)
                            population[next_col][row] = -1;
                    }
                    if (previous_row >= 0) {
                        if (population[col][previous_row] <= strength)
                            population[col][previous_row] = -1;
                    }
                    if (next_row < population[col].length) {
                        if (population[col][next_row] <= strength)
                            population[col][next_row] = -1;
                    }
                }   
            }
        }
        return population;
    }