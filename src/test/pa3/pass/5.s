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

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #76
    ldr a1, =#1
    str a1, [fp, #-28]
    ldr a1, =#2
    str a1, [fp, #-32]
    ldr a1, =#3
    str a1, [fp, #-36]
    ldr a1, =#4
    str a1, [fp, #-40]
    ldr a1, =#5
    str a1, [fp, #-44]
    ldr a1, =#6
    str a1, [fp, #-48]
    ldr a1, =#7
    str a1, [fp, #-52]
    ldr a1, =#8
    str a1, [fp, #-56]
    ldr a1, [fp, #-56]
    str a1, [fp, #-60]
    ldr a1, [fp, #-52]
    str a1, [fp, #-64]
    ldr a1, [fp, #-48]
    str a1, [fp, #-68]
    ldr a1, [fp, #-44]
    str a1, [fp, #-72]
    ldr v3, [fp, #-40]
    ldr v4, [fp, #-36]
    ldr v5, [fp, #-32]
    ldr v2, [fp, #-28]
    add v1, v5, v2
    add v2, v4, v1
    add v1, v3, v2
    ldr a1, [fp, #-72]
    add v2, a1, v1
    ldr a1, [fp, #-68]
    add v1, a1, v2
    ldr a1, [fp, #-64]
    add v2, a1, v1
    ldr a1, [fp, #-60]
    add v1, a1, v2
    ldr a1, [fp, #-56]
    add v2, a1, v1
    ldr a1, [fp, #-52]
    add v1, a1, v2
    ldr a1, [fp, #-48]
    add v2, a1, v1
    ldr a1, [fp, #-44]
    add v1, a1, v2
    ldr a1, [fp, #-40]
    add v2, a1, v1
    ldr a1, [fp, #-36]
    add v1, a1, v2
    ldr a1, [fp, #-32]
    add v2, a1, v1
    ldr a1, [fp, #-28]
    add v1, a1, v2
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

