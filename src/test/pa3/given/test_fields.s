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

FieldAccess_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v2, a1
    mov v1, a2
    ldr a2, =#6
    add a1, v1, a2
    b FieldAccess_0_exit

FieldAccess_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov a2, #8
    mov a1, #1
    bl calloc(PLT)
    mov v2, a1
    ldr a2, =#5
    mov a1, v2
    bl FieldAccess_0(PLT)
    mov v3, a1
    mov a2, v2
    ldr a1, =#7
    str a1, [a2, #4]
    ldr v1, [v2, #4]
    add v2, v3, v1
    ldr a1, =#2
    mul v3, a1, v2
    mov a2, v3
    ldr a1, =_int
    bl printf(PLT)

FieldAccessMain_0_exit:
    mov a1, #0
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end
