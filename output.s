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
S1:
    .asciz "Square of d smaller than sum of squares\n"
S2:
    .asciz "Square of d larger than sum of squares\n"

    .text
    .global main
    .type main, %function

Compute_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #36
    str a1, [fp, #-28]
    str a2, [fp, #-32]
    ldr v1, [fp, #-32]
    mov v2, v1
    ldr v1, [fp, #-32]
    mov v3, v1
    mul v1, v2, v3
    mov a1, v1
    b Compute_0_exit

Compute_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Compute_1:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #40
    str a1, [fp, #-28]
    str a2, [fp, #-32]
    str a3, [fp, #-36]
    ldr v1, [fp, #-36]
    mov v2, v1
    ldr v1, [fp, #-32]
    mov v3, v1
    add v1, v2, v3
    mov a1, v1
    b Compute_1_exit

Compute_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Compute_2:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #48
    str a1, [fp, #-28]
    str a2, [fp, #-32]
    str a3, [fp, #-36]
    ldr v4, [fp, #-28]
    ldr v1, [v4, #4]
    cmp v1, #1
    beq l1
    mov v1, #1
    ldr v4, [fp, #-28]
    str v1, [v4, #4]
    ldr v1, [fp, #-28]
    mov a1, v1
    ldr v1, [fp, #-32]
    mov a2, v1
    bl Compute_0(PLT)
    mov v1, a1
    str v1, [fp, #-40]
    ldr v1, [fp, #-28]
    mov a1, v1
    ldr v1, [fp, #-36]
    mov a2, v1
    bl Compute_0(PLT)
    mov v1, a1
    str v1, [fp, #-44]
    ldr v1, [fp, #-28]
    mov a1, v1
    ldr v1, [fp, #-40]
    mov a2, v1
    ldr v1, [fp, #-44]
    mov a3, v1
    bl Compute_1(PLT)
    mov v1, a1
    mov a1, v1
    b Compute_2_exit
    b l2
l1:
    ldr v4, [fp, #-28]
    ldr v1, [v4, #8]
    mov a1, v1
    b Compute_2_exit
l2:

Compute_2_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #68
    ldr v1, =#1
    str v1, [fp, #-28]
    ldr v1, =#2
    str v1, [fp, #-32]
    ldr v1, =#3
    str v1, [fp, #-36]
    ldr v1, =#4
    str v1, [fp, #-40]
    mov a1, #12
    bl malloc(PLT)
    mov v1, a1
    str v1, [fp, #-52]
    ldr v1, [fp, #-52]
    mov a1, v1
    ldr v1, [fp, #-28]
    mov a2, v1
    ldr v1, [fp, #-32]
    mov a3, v1
    bl Compute_2(PLT)
    mov v1, a1
    str v1, [fp, #-56]
    ldr v1, [fp, #-52]
    mov a1, v1
    ldr v1, [fp, #-36]
    mov a2, v1
    bl Compute_0(PLT)
    mov v1, a1
    str v1, [fp, #-60]
    ldr v1, [fp, #-60]
    mov v2, v1
    ldr v1, [fp, #-56]
    mov v3, v1
    add v1, v2, v3
    str v1, [fp, #-44]
    ldr v1, [fp, #-52]
    mov a1, v1
    ldr v1, [fp, #-40]
    mov a2, v1
    bl Compute_0(PLT)
    mov v1, a1
    str v1, [fp, #-48]
    ldr v1, [fp, #-44]
    mov v2, v1
    ldr v1, [fp, #-48]
    mov v3, v1
    mov v1, #0
    cmp v2, v3
    movlt v1, #1
    str v1, [fp, #-64]
    ldr v1, [fp, #-64]
    cmp v1, #1
    beq l3
    ldr v1, =S1
    mov a2, v1
    ldr a1, =_string
    bl printf(PLT)
    b l4
l3:
    ldr v1, =S2
    mov a2, v1
    ldr a1, =_string
    bl printf(PLT)
l4:

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

