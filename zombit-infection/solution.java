/**
 *  Note: Google makes a claim that x = column, y = row. But this results in a confusing
 *  accessor for population, requiring population[y][x] rather than population[x][y]
 *
 *  Solution on this page is pretty much guaranteed to be inefficient, but it works.
 */
public static int[][] answer(int[][] population, int x, int y, int strength) { 

        // Your code goes here.
        // Check to see if initial infection even happens, if not return population
        if (strength < population[y][x])
            return population;
            
        population[y][x] = -1;
        while (true) {
        	int[][] population_pass = array_deep_copy(population);
        	int[][] infection_cycle = run_infection_pass(population, strength);
            if (arrays_equal(population_pass, infection_cycle))
                return infection_cycle;
            
            population = infection_cycle;
        }
    }
    
    private static boolean arrays_equal(int[][] base, int[][] comparison) {
        // We're never going to have arrays of non-equal dimensions
        for (int i = 0; i < base.length; i++) {
            for (int j = 0; j < base[i].length; j++) {
                if (base[i][j] != comparison[i][j])
                    return false;
            }
        }
        return true;
    }
    
    private static int[][] run_infection_pass(int[][] population, int strength) {
        for (int col = 0; col < population.length; col++) {
            for (int row = 0; row < population[col].length; row++) {
                if (population[col][row] == -1) {
                    // infect neighbors
                    int previous_col = col - 1;
                    int next_col = col + 1;
                    int previous_row = row - 1;
                    int next_row = row + 1;
                    
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
    
    private static int[][] array_deep_copy(int[][] original) {
    	int[][] holder = new int[original.length][];
    	for (int i = 0; i < original.length; i++) {
    		holder[i] = new int[original[i].length];
    		for (int j = 0; j < original[i].length; j++) {
    			holder[i][j] = original[i][j];
    		}
    	}
    	return holder;
    }