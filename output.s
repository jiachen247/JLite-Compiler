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
    .asciz "null\n"
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
    sub sp, fp, #28
    mov v2, a1
    mov v4, a2
    mov v3, a3
    mov v1, v2
    ldr v1, [v1, #4]
    cmp v1, #1
    beq l1
    mov a2, v2
    mov a1, #1
    str a1, [a2, #4]
    mov a2, v4
    mov a1, v2
    bl Compute_0(PLT)
    mov v5, a1
    mov a2, v3
    mov a1, v2
    bl Compute_0(PLT)
    mov v1, a1
    mov a2, v2
    mov a3, v1
    mov a2, v5
    mov a1, v2
    bl Compute_1(PLT)
    mov a1, a1
    str a1, [a2, #8]
    mov a1, v2
    ldr a1, [a1, #8]
    b Compute_2_exit
    b l2
l1:
    mov a1, v2
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
    ldr v2, =#2
    ldr v1, =#3
    ldr v1, =#4
    mov a2, #12
    mov a1, #1
    bl calloc(PLT)
    mov v4, a1
    mov a3, v2
    mov a2, v5
    mov a1, v4
    bl Compute_2(PLT)
    mov v3, a1
    mov a2, v1
    mov a1, v4
    bl Compute_0(PLT)
    mov v2, a1
    mov a2, v2
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    mov a2, v3
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    mov v1, #0
    cmp v3, v2
    movlt v1, #1
    cmp v1, #1
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

