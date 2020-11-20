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

    .text
    .global main
    .type main, %function

Dummy_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    ldr v4, [fp, #4]
    ldr v5, [fp, #28]
    ldr a2, [fp, #8]
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    ldr a2, [fp, #12]
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    ldr a2, [fp, #16]
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    ldr a2, [fp, #20]
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    ldr a2, [fp, #24]
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    mov a2, v5
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    ldr a1, [fp, #12]
    ldr a2, [fp, #8]
    mul v2, a1, a2
    ldr a1, [fp, #20]
    ldr a2, [fp, #16]
    mul v3, a1, a2
    add v1, v3, v2
    ldr a1, [fp, #24]
    add a1, a1, v1
    b Dummy_0_exit

Dummy_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov a1, #4
    bl malloc(PLT)
    mov v2, a1
    ldr a1, =#6
    push {a1}
    ldr a1, =#5
    push {a1}
    ldr a1, =#4
    push {a1}
    ldr a1, =#3
    push {a1}
    ldr a1, =#2
    push {a1}
    ldr a1, =#1
    push {a1}
    push {v2}
    bl Dummy_0(PLT)
    add sp, sp, #28
    mov v1, a1
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

