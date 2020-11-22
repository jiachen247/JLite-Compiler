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
S1:
    .asciz "Square of d larger than sum of squares"
S2:
    .asciz "
Factorial of:"
S3:
    .asciz " equal to:"
S4:
    .asciz "
"

    .text
    .global main
    .type main, %function

Compute_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v1, a1
    mov v2, a2
    mul a1, v2, v2
    b Compute_0_exit

Compute_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Compute_1:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v1, a1
    mov v3, a2
    mov v2, a3
    add a1, v2, v3
    b Compute_1_exit

Compute_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Compute_2:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v3, a1
    mov v5, a2
    mov v4, a3
    ldr v1, [v3, #4]
    cmp v1, #1
    beq l1
    mov v1, #1
    mov a2, v3
    mov a1, v1
    str a1, [a2, #4]
    mov a2, v5
    mov a1, v3
    bl Compute_0(PLT)
    mov v1, a1
    mov a2, v4
    mov a1, v3
    bl Compute_0(PLT)
    mov v2, a1
    mov a3, v2
    mov a2, v1
    mov a1, v3
    bl Compute_1(PLT)
    b Compute_2_exit
    b l2
l1:
    ldr a1, [v3, #8]
    b Compute_2_exit
l2:

Compute_2_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Compute_3:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v2, a1
    mov v3, a2
    ldr a1, =#1
    mov v4, #0
    cmp a1, v3
    movgt v4, #1
    cmp v4, #1
    beq l3
    ldr a1, =#1
    sub v5, v3, a1
    mov a2, v5
    mov a1, v2
    bl Compute_3(PLT)
    mov v4, a1
    mul v1, v4, v3
    mov a1, v1
    b Compute_3_exit
    b l4
l3:
    ldr v1, =#1
    mov a1, v1
    b Compute_3_exit
l4:

Compute_3_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    ldr v3, =#1
    ldr v2, =#2
    ldr v1, =#3
    ldr v4, =#4
    push {a3}
    mov a2, #12
    mov a1, #1
    bl calloc(PLT)
    mov v5, a1
    pop {a3}
    push {a3}
    mov a3, v2
    mov a2, v3
    mov a1, v5
    bl Compute_2(PLT)
    pop {a3}
    mov a3, a1
    push {a3}
    mov a2, v1
    mov a1, v5
    bl Compute_0(PLT)
    pop {a3}
    mov v3, a1
    add v2, v3, a3
    push {a3}
    mov a2, v4
    mov a1, v5
    bl Compute_0(PLT)
    pop {a3}
    mov v1, a1
    mov v3, #0
    cmp v2, v1
    movlt v3, #1
    cmp v3, #1
    beq l5
    push {a3}
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
    b l6
l5:
    push {a3}
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
l6:
    push {a3}
    ldr a2, =#4
    mov a1, v5
    bl Compute_3(PLT)
    pop {a3}
    mov v2, a1
    push {a3}
    ldr a2, =S2
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
    push {a3}
    ldr a2, =#4
    ldr a1, =_int
    bl printf(PLT)
    pop {a3}
    push {a3}
    ldr a2, =S3
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
    push {a3}
    mov a2, v2
    ldr a1, =_int
    bl printf(PLT)
    pop {a3}
    push {a3}
    ldr a2, =S4
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}

Main_0_exit:
    mov a1, #0
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end
