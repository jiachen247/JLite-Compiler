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
    .asciz "Square of d smaller than sum of squares
"
S2:
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
    push {a3}
    mov a2, v5
    mov a1, v3
    bl Compute_0(PLT)
    pop {a3}
    mov v1, a1
    push {a3}
    mov a2, v4
    mov a1, v3
    bl Compute_0(PLT)
    pop {a3}
    mov v2, a1
    push {a3}
    mov a3, v2
    mov a2, v1
    mov a1, v3
    bl Compute_1(PLT)
    pop {a3}
    mov a3, a1
    mov a2, v3
    mov a1, a3
    str a1, [a2, #8]
    ldr a1, [v3, #8]
    b Compute_2_exit
    b l2
l1:
    ldr a1, [v3, #8]
    b Compute_2_exit
l2:

Compute_2_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    ldr a3, =#1
    ldr v4, =#2
    ldr v1, =#3
    ldr v3, =#4
    push {a3}
    mov a2, #12
    mov a1, #1
    bl calloc(PLT)
    mov v5, a1
    pop {a3}
    push {a3}
    mov a3, v4
    mov a2, a3
    mov a1, v5
    bl Compute_2(PLT)
    pop {a3}
    mov v2, a1
    push {a3}
    mov a2, v3
    mov a1, v5
    bl Compute_0(PLT)
    pop {a3}
    mov v1, a1
    mov v3, #0
    cmp v2, v1
    movlt v3, #1
    cmp v3, #1
    beq l3
    push {a3}
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
    b l4
l3:
    push {a3}
    ldr a2, =S2
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
l4:

Main_0_exit:
    mov a1, #0
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end
