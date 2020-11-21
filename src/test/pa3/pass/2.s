    .data
_int:
    .asciz "%d\n"
_string:
    .asciz "%s"
_true:
    .asciz "true\n"
_false:
    .asciz "false\n"
_null:
    .asciz "null\n"
_empty:
    .asciz ""
_newline:
    .asciz "\\n"

    .text
    .global main
    .type main, %function

Factorial_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v4, a1
    mov v2, a2
    ldr a1, =#1
    mov v1, #0
    cmp a1, v2
    moveq v1, #1
    cmp v1, #1
    beq l1
    ldr a1, =#1
    sub v3, v2, a1
    mov a2, v3
    mov a1, v4
    bl Factorial_0(PLT)
    mov v1, a1
    mul a1, v1, v2
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
    sub sp, fp, #28
    mov v3, a1
    mov v1, a2
    ldr v4, =#1
    b l4
l3:
    mul v4, v1, v4
    ldr a1, =#1
    sub v1, v1, a1
l4:
    ldr a1, =#0
    mov v2, #0
    cmp a1, v1
    movlt v2, #1
    cmp v2, #1
    beq l3
    mov a1, v4
    b Factorial_1_exit

Factorial_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov a2, #4
    mov a1, #1
    bl calloc(PLT)
    mov v1, a1
    ldr a2, =#5
    mov a1, v1
    bl Factorial_0(PLT)
    mov v2, a1
    mov a2, v2
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =#5
    mov a1, v1
    bl Factorial_1(PLT)
    mov v2, a1
    mov a2, v2
    ldr a1, =_int
    bl printf(PLT)
    b Main_0_exit

Main_0_exit:
    mov a1, #0
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end
