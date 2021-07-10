// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

(RESET)
@SCREEN
D=A-1
@address // address = SCREEN
M=D

(LOOP)
@address // loop condition: address < KBD
D=M
@KBD
D=D-A
@RESET // is a loop condition necessary at all, need to keep looping.
D; JEQ // can i somehow get modular arithmetic on the address

@KBD
D=M
@NOT_PRESSED
D; JEQ // If no key is pressed. D = 0 (White) so proceed to write screen memory

D=-1 // Set data register to black color

(NOT_PRESSED)
@address
AM=M+1 // store address in address register and increment RAM[A]
M=D // set the pixels to stored color: 0 (White) or -1 (Black)

@LOOP
0; JMP
