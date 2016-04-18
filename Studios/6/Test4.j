.class public Test4
.super java/lang/Object

;
;  Team members:
;
;
;    Answer question:
;        9)  Assuming big-endian format,
;             what is the value in hex of the first 4 bytes of a .class file?
;
;    Handle question 10) below, and turn in this file
;
;    Consider the contest as explained below
;

;
; standard initializer
;
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static bar()I
   .limit stack  3
   .limit locals 0

   bipush 100
   bipush 4
   imul
   bipush 31
   iadd
  ireturn
.end method

;
;  Do not change the signature of this method
;
.method public static foo(I)V
   .limit locals 3 
   .limit stack  2

   ;
   ;    reg 0 - the incoming parameter
   ;    reg 1 - the System.out PrintStream object 
   ;               (really known as java.lang.System.out)
   ;    reg 2 - a temporary
   ;

   getstatic java/lang/System/out Ljava/io/PrintStream;
   astore 1

   ;
   ;  push the supplied parameter on top of stack
   ;
   iload 0
   ;
   ;  convert it to a String
   ;
   invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
   ;
   ;   that string returned from valueOf(I) is now on top of stack
   ;
   astore 2  ;  store the result so we can print it below
   ;
   ; ... and print it
   ;
   aload 1    ; push the PrintStream object
   aload 2    ; push the string we just created and then print it
   invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V
   ;
   ; 10) Rewrite the above code to print integers, starting at the
   ;       value of the supplied parameter, and counting down to 0,
   ;       including 0.  Print just one integer per line.
   ;
   ;
   return
.end method

;
;  Do not change any code below this line
;
.method public static main([Ljava/lang/String;)V
       ; set limits used by this method
       .limit locals 1   ; = parameters + locals
       .limit stack  1
        invokestatic Test4/bar()I 
        invokestatic Test4/foo(I)V
        return
.end method
