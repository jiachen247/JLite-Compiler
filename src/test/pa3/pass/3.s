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

    .text
    .global main
    .type main, %function

Dummy_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v2, a1
    mov v5, a2
    mov v4, a3
    mov v3, a4
    add v1, v4, v5
    add a1, v3, v1
    b Dummy_0_exit

Dummy_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Dummy_1:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #32
    ldr v4, [fp, #4]
    ldr v5, [fp, #32]
    ldr v3, [fp, #36]
    ldr a1, [fp, #16]
    ldr a2, [fp, #12]
    mul v2, a1, a2
    ldr a2, [fp, #8]
    sub v1, a2, v2
    ldr a1, [fp, #24]
    ldr a2, [fp, #20]
    mul v2, a1, a2
    add a1, v2, v1
    str a1, [fp, #-28]
    ldr a1, [fp, #28]
    ldr a2, [fp, #-28]
    add v2, a1, a2
    add v1, v5, v2
    add a1, v3, v1
    b Dummy_1_exit

Dummy_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov a2, #4
    mov a1, #1
    bl calloc(PLT)
    mov v2, a1
    ldr a4, =#3
    ldr a3, =#2
    ldr a2, =#1
    mov a1, v2
    bl Dummy_0(PLT)
    mov v1, a1
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)
    ldr v3, =#1
    ldr a1, =#0
    push {a1}
    ldr a1, =#0
    push {a1}
    ldr a1, =#6
    push {a1}
    ldr a1, =#5
    push {a1}
    ldr a1, =#4
    push {a1}
    ldr a1, =#3
    push {a1}
    push {v3}
    ldr a1, =#1000
    push {a1}
    push {v2}
    bl Dummy_1(PLT)
    add sp, sp, #36
    mov v1, a1
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)

Mainnnn_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

