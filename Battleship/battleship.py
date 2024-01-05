''' File: battleship.py
    Author: Katana Bierman
    Purpose: This program simulates a battleship game.
    CSC 120 Fall 2021
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
        board_data = {}
        for y_coord in range(size):
            for x_coord in range(size):
                # value is (ship, hit status)
                board_data[(x_coord, y_coord)] = [None, False, None]
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
            for coords in self._board_data:
                if (ship_x, ship_y) == coords:
                    assert self._board_data[coords][0] is None
                    ship_data.append((ship_x, ship_y))
        # verifies whole ship could be added
        index = 0
        if len(ship_data) == len(ship_shape):
            # loops through and replaces old coord data with new data
            for data in ship_data:
                self._board_data[data] = [ship, False, index]
                index += 1

    def print(self):
        ''' This method prints out the current state of the board.
        '''
        size_digits = len(str(self._size))
        # prints top row
        print(' ' * (size_digits-1) + '  +' + '-' * (self._size*2) + '-+')
        y_num = self._size-1
        x_num = 0
        # loops through each y-value
        for i in range(self._size):
            print_str = ' ' * (size_digits-len(str(y_num))) + str(y_num) \
            + ' | '
            # prints data for each coordinate
            for j in range(self._size):
                if self._board_data[(j, y_num)][0] is None:
                    if self._board_data[(j, y_num)][1] is False:
                        print_str += '. '
                    elif self._board_data[(j, y_num)][1] is True:
                        print_str += 'o '
                elif self._board_data[(j, y_num)][0].is_sunk():
                    print_str += 'X '
                elif self._board_data[(j, y_num)][1] is True:
                    print_str += '* '
                else:
                    first_letter = self._board_data[(j, y_num)][0].name[0]
                    print_str += (first_letter + ' ')
            print_str += '|'
            y_num -= 1
            print(print_str)
        # prints closing row
        print(' ' * (size_digits-1) + '  +' + '-' * (self._size*2) + '-+')
        # prints bottom row
        row_num = size_digits
        # if size is a one of the powers of 10, it subtracts one from the
        # number of rows
        if self._size % 10 == 0 and str(self._size)[0] == '1':
            row_num -= 1
        # creates a list of every element as an x coord as a string
        nums_list = []
        for i in range(self._size):
            nums_list.append(str(i))
        # loops through each digit row, highest to lowest
        for i in range(row_num):        
            print_str = ' ' * (size_digits-1) + '    '
            for j in range(len(nums_list)):
                if len(nums_list[j]) == row_num:
                    print_str += (nums_list[j][0] + ' ')
                    # slices 1st int off string to lower number of digits
                    nums_list[j] = nums_list[j][1:]
                else:
                    print_str += '  '
            print(print_str)
            row_num -= 1

    def has_been_used(self, position):
        ''' This method determines if a spot has been shot at in the game
            Argument:
            position- a tuple of (x, y) coordinates
        '''
        return self._board_data[position][1]

    def attempt_move(self, position):
        ''' This method executes a move done by the player. 
            It displays if the move was a miss, a hit that sunk
            a ship, or a hit on a ship.
        '''
        # asserts move is on a valid position
        assert position[0] < self._size and position[1] < self._size
        assert self._board_data[position][1] is False
        ship = self._board_data[position][0]
        self._board_data[position][1] = True
    
        if ship is None:
            return 'Miss'
        else:
            ship.hit_index.append(self._board_data[position][2])
            if ship.is_sunk():
                return 'Sunk ' + '(' +  ship.name + ')'
            else:
                return 'Hit'

class Ship:
    def __init__(self, name, shape):
        ''' This method is the constructor of the ship.
            Arguments:
            name- a str of the ship's name
            shape- a list of (x, y) relative coords to the
            ship
        '''
        self.name = name
        self.shape = shape
        self.hit_index = []
    
    def print(self):
        ''' This method prints the status of the ship
            by showing the spaces that were hit
        '''
        print_str = ''
        for coords in self.shape:
            if self.shape.index(coords) in self.hit_index:
                print_str += '*'
            else:
                print_str += self.name[0]
        print_str += (' ' * (10 - len(print_str)))
        print(print_str + self.name)

    def is_sunk(self):
        ''' This method determines if the ship has been sunk
        '''
        if len(self.hit_index) == len(self.shape):
            return True
        return False

    def rotate(self, amount):
        ''' This method rotates the ship 90-degrees clockwise
        '''
        new_coords = self.shape
        for i in range(amount):
            for j in range(len(new_coords)):
                new_x = new_coords[j][1]
                new_y = (new_coords[j][0] * -1)
                new_coords[j] = (new_x, new_y)
        self.shape = new_coords

