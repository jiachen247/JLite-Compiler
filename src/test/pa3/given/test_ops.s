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
    sub sp, fp, #28
    ldr a1, =#2
    ldr a2, =#1
    add v1, a1, a2
    ldr a1, =#3
    add v2, a1, v1
    ldr a1, =#4
    add v1, a1, v2
    ldr a1, =#5
    add v2, a1, v1
    ldr a1, =#6
    add v1, a1, v2
    ldr a1, =#7
    add v2, a1, v1
    ldr a1, =#8
    add v1, a1, v2
    ldr a1, =#9
    add v2, a1, v1
    ldr a1, =#10
    add v1, a1, v2
    ldr a1, =#11
    add v2, a1, v1
    ldr a1, =#12
    add v1, a1, v2
    ldr a1, =#13
    add v2, a1, v1
    ldr a1, =#14
    add v1, a1, v2
    ldr a1, =#15
    add v2, a1, v1
    ldr a1, =#16
    add v1, a1, v2
    ldr a1, =#17
    add v2, a1, v1
    ldr a1, =#18
    add v1, a1, v2
    ldr a1, =#19
    add v2, a1, v1
    ldr a1, =#20
    add v3, a1, v2
    mov a2, v3
    ldr a1, =_int
    bl printf(PLT)

LongAdditionMain_0_exit:
    mov a1, #0
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end
