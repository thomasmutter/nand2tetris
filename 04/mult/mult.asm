// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

// mult a*b
// i = 0
// result = 0
// n=b

// LOOP:
// if i > n goto END
// result = result + a

@i
M=0

@result
M=0

(LOOP)
@i
D=M
@R1
D=D-M
@STOP
D; JEQ  // loop condition

@i
M=M+1 // i = i + 1

@R0
D=M
@result
M=M+D  // result = result + a

@LOOP
0; JMP

(STOP)
@result
D=M
@R2
M=D //store result in R2

(END)
@END
0; JMP

