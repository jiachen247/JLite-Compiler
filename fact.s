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
    .asciz "hello there!\n"

    .text
    .global main
    .type main, %function

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    ldr v1, =#12345
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)
    ldr v1, =S1
    mov a2, v1
    ldr a1, =_string
    bl printf(PLT)
    mov v1, #1
    cmp v1, #1
    ldreq a1, =_true
    ldrne a1, =_false
    bl printf(PLT)
    mov v1, #0
    cmp v1, #1
    ldreq a1, =_true
    ldrne a1, =_false
    bl printf(PLT)

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

