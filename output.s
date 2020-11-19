    .data
_int:
    .asciz "%d"
_string:
    .asciz "%s"
_true:
    .asciz "true"
_false:
    .asciz "false"
_null:
    .asciz "null"
_empty:
    .asciz ""
_newline:
    .asciz "\\n"
S1:
    .asciz "
"

    .text
    .global main
    .type main, %function

Factorial_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #48
    str a1, [fp, #-28]
    str a2, [fp, #-32]
    ldr a1, =#1
    mov a2, a1
    ldr v1, [fp, #-32]
    mov a1, v1
    mov a3, a1
    mov a1, #0
    cmp a2, a3
    moveq a1, #1
    str a1, [fp, #-36]
    ldr v2, [fp, #-36]
    mov a1, v2
    cmp a1, #1
    beq l1
    ldr a1, =#1
    mov a2, a1
    mov a1, v1
    mov a3, a1
    sub a1, a3, a2
    str a1, [fp, #-40]
    ldr v3, [fp, #-40]
    mov a1, v3
    mov a2, a1
    ldr v4, [fp, #-28]
    mov a1, v4
    bl Factorial_0(PLT)
    str a1, [fp, #-44]
    ldr v5, [fp, #-44]
    mov a1, v5
    mov a2, a1
    mov a1, v1
    mov a3, a1
    mul a1, a2, a3
   
    b Factorial_0_exit
    b l2
l1:
    ldr a1, =#1
   
    b Factorial_0_exit
l2:

Factorial_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Factorial_1:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #40
    str a1, [fp, #-28]
    str a2, [fp, #-32]
    ldr a1, =#1
    str a1, [fp, #-36]
    b l4
l3:
    ldr v1, [fp, #-32]
    mov a1, v1
    mov a2, a1
    ldr a1, =_int
    bl printf(PLT)
    mov a1, v1
    mov a2, a1
    ldr v2, [fp, #-36]
    mov a1, v2
    mov a3, a1
    mul a1, a2, a3
    mov v2, a1
    ldr a1, =#1
    mov a2, a1
    mov a1, v1
    mov a3, a1
    sub a1, a3, a2
    mov v1, a1
    mov a1, v1
    mov a2, a1
    ldr a1, =_int
    bl printf(PLT)
l4:
    ldr a1, =#0
    mov a2, a1
    mov a1, v1
    mov a3, a1
    mov a1, #0
    cmp a2, a3
    movlt a1, #1
    cmp a1, #1
    beq l3
    mov a1, v2
   
    b Factorial_1_exit

Factorial_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #40
    mov a1, #4
    bl malloc(PLT)
    str a1, [fp, #-28]
    ldr a1, =#5
    mov a2, a1
    ldr v1, [fp, #-28]
    mov a1, v1
    bl Factorial_0(PLT)
    str a1, [fp, #-32]
    ldr v2, [fp, #-32]
    mov a1, v2
    mov a2, a1
    ldr a1, =_int
    bl printf(PLT)
    ldr a1, =S1
    mov a2, a1
    ldr a1, =_string
    bl printf(PLT)
    ldr a1, =#5
    mov a2, a1
    mov a1, v1
    bl Factorial_1(PLT)
    str a1, [fp, #-36]
    ldr v3, [fp, #-36]
    mov a1, v3
    mov a2, a1
    ldr a1, =_int
    bl printf(PLT)
    b Main_0_exit

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

