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
S2:
    .asciz "Square of d smaller than sum of squares
"
S3:
    .asciz "Square of d larger than sum of squares
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
    sub sp, fp, #44
    mov v4, a1
    str a2, [fp, #-28]
    str a3, [fp, #-32]
    mov v3, v4
    ldr v3, [v3, #4]
    cmp v3, #1
    beq l1
    mov a4, v4
    mov a1, #1
    str a1, [a4, #4]
    ldr a2, [fp, #-28]
    mov a1, v4
    bl Compute_0(PLT)
    mov v2, a1
    ldr a2, [fp, #-32]
    mov a1, v4
    bl Compute_0(PLT)
    mov v1, a1
    mov a4, v4
    mov a3, v1
    mov a2, v2
    mov a1, v4
    bl Compute_1(PLT)
    mov a1, a1
    str a1, [a4, #8]
    mov a1, v4
    ldr a1, [a1, #8]
    b Compute_2_exit
    b l2
l1:
    mov a1, v4
    ldr a1, [a1, #8]
    b Compute_2_exit
l2:

Compute_2_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    ldr v5, =#1
    ldr v1, =#2
    ldr v2, =#3
    ldr v3, =#4
    mov a1, #12
    bl malloc(PLT)
    mov v4, a1
    mov a3, v1
    mov a2, v5
    mov a1, v4
    bl Compute_2(PLT)
    mov v2, a1
    mov a2, v3
    mov a1, v4
    bl Compute_0(PLT)
    mov v1, a1
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    mov a2, v2
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    mov v3, #0
    cmp v2, v1
    movlt v3, #1
    cmp v3, #1
    beq l3
    ldr a2, =S2
    ldr a1, =_string
    bl printf(PLT)
    b l4
l3:
    ldr a2, =S3
    ldr a1, =_string
    bl printf(PLT)
l4:

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

