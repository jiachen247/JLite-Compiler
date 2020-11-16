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

A_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #36
    str a1, [fp, #-28]
    ldr v1, =#1
    ldr v4, [fp, #-28]
    str v1, [v4, #4]
    ldr v1, [fp, #-28]
    mov v4, v1
    ldr v1, =#20
    str v1, [v4, #8]
    ldr v1, [fp, #-28]
    mov v4, v1
    ldr v1, [v4, #8]
    str v1, [fp, #-32]
    ldr v1, [fp, #-28]
    mov v4, v1
    ldr v1, =#100
    mov v2, v1
    ldr v1, [fp, #-32]
    mov v3, v1
    add v1, v2, v3
    str v1, [v4, #12]

A_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

A_1:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #36
    str a1, [fp, #-28]
    ldr v4, [fp, #-28]
    ldr v1, [v4, #8]
    mov v2, v1
    ldr v4, [fp, #-28]
    ldr v1, [v4, #4]
    mov v3, v1
    add v1, v2, v3
    str v1, [fp, #-32]
    ldr v4, [fp, #-28]
    ldr v1, [v4, #12]
    mov v2, v1
    ldr v1, [fp, #-32]
    mov v3, v1
    add v1, v2, v3
    mov a1, v1
    b A_1_exit

A_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #36
    mov a1, #16
    bl malloc(PLT)
    mov v1, a1
    str v1, [fp, #-28]
    ldr v1, [fp, #-28]
    mov a1, v1
    bl A_0(PLT)
    mov v1, a1

    ldr v1, [fp, #-28]
    mov a1, v1
    bl A_1(PLT)
    mov v1, a1
    str v1, [fp, #-32]
    ldr v1, [fp, #-32]
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

