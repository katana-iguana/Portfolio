''' File: battleship.py
    Author: Katana Bierman
    Purpose: simulates a game of battleship by displaying one
    board with ships as they are hit or not 
    CSC 120
'''

class Board:
    def __init__(self, size):
        ''' This method is a constructor for the battlship board.
            The board built is an empty board with a square shape.
            Argument:
            size- any integer for the length of the board
        '''
        assert size > 0
        self._size = size
        # creates data for each coordinate in the board
        board_data = []
        for y_coord in range(size):
            for x_coord in range(size):
                board_data.append([(x_coord, y_coord), None, False])
        self._board_data = board_data
    
    def add_ship(self, ship, position):
        ''' This method adds a ship at a given (x, y) position
            Arguments:
            ship- a Ship object
            postition- a tuple of the ship's (x, y) starting position
        '''
        ship_shape = ship.shape
        ship_data = []
        for coords in ship_shape:
            ship_x = coords[0]+position[0]
            ship_y = coords[1]+position[1]
            # checks that possible coords are in valid indices
            assert ship_x < self._size and ship_y < self._size
            for coord_data in self._board_data:
                if (ship_x, ship_y) in coord_data:
                    assert coord_data[1] is None
                    ship_data.append([(ship_x, ship_y), None, False])
        # verifies whole ship could be added
        if len(ship_data) == len(ship_shape):
            # loops through and replaces old coord data with new data
            for data in ship_data:
                coords_index = self._board_data.index(data)
                self._board_data[coords_index] = [data[0], ship, False]

    def print(self):
        ''' This method prints out the current state of the board.
        '''
        size_digits = len(str(self._size))
        # prints top row
        print(' ' * (size_digits-1) + '  +' + '-' * (self._size*2) + '-+')
        y_num = self._size-1
        x_num = 0
        # prints middle rows
        for i in range(self._size):
            print_str = ' ' * (size_digits-len(str(y_num))) + str(y_num) + ' | '
            for j in range(self._size):
                print_str += '. '
            print_str += '|'
            y_num-=1
            print(print_str)
        print(' ' * (size_digits-1) + '  +' + '-' * (self._size*2) + '-+')
        # prints bottom row
        x_num = 0
        print_str = ' ' * (size_digits-1) + '    '
        while x_num < self._size:
            print_str += (str(x_num) + ' ')
            x_num += 1
        print(print_str)


        

class Ship:
    def __init__(self, name, shape):
        self.name = name
        self.shape = shape

board = Board(3)
ship = Ship("Destroyer", [(0,0), (1,0)])
board.add_ship(ship, (1, 0))
board.print()