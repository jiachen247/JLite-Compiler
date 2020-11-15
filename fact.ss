    .data
_int:
    .asciz "%d\n"
_string:
    .asciz "%s\n"
_true:
    .asciz "true\n"
_false:
    .asciz "false\n"
_null:
    .asciz "null"

    .text
    .global main
    .type main, %function

Factorial_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #20
    str a1, [fp, #-24]
    str a2, [fp, #-28]
    mov v1, #1
    mov v2, v1
    ldr v1, [fp, #-28]
    mov v3, v1
    mov v1, #0
    cmp v2, v3
    moveq v1, #1
    str v1, [fp, #-32]
    ldr v1, [fp, #-32]
    cmp v1, #1
    beq l1
    mov v1, #1
    mov v2, v1
    ldr v1, [fp, #-28]
    mov v3, v1
    sub v1, v3, v2
    str v1, [fp, #-36]
    ldr v1, [fp, #-24]
    mov a1, v1
    ldr v1, [fp, #-36]
    mov a2, v1
    bl Factorial_0(PLT)
    mov v1, a1
    str v1, [fp, #-40]
    ldr v1, [fp, #-40]
    mov v2, v1
    ldr v1, [fp, #-28]
    mov v3, v1
    mul v1, v2, v3
    mov a1, v1
    b Factorial_0_exit
    b l2
l1:
    mov v1, #1
    mov a1, v1
    b Factorial_0_exit
l2:

Factorial_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #8
    mov a1, #4
    bl malloc(PLT)
    mov v1, a1
    str v1, [fp, #-24]
    ldr v1, [fp, #-24]
    mov a1, v1
    mov v1, #5
    mov a2, v1
    bl Factorial_0(PLT)
    mov v1, a1
    str v1, [fp, #-28]
    ldr v1, [fp, #-28]
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

