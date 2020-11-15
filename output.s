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

Test_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    ldr v1, [fp, #8]
    cmp v1, #1
    ldreq a1, =_true
    ldrne a1, =_false
    bl printf(PLT)
    b Test_0_exit

Test_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #4
    mov a1, #4
    bl malloc(PLT)
    mov v1, a1
    str v1, [fp, #24]
    mov v1, #8
    push {v1}
    mov v1, #7
    push {v1}
    mov v1, #6
    push {v1}
    mov v1, #5
    push {v1}
    mov v1, #4
    push {v1}
    mov v1, #3
    push {v1}
    mov v1, #2
    push {v1}
    mov v1, #1
    push {v1}
    mov v1, #0
    push {v1}
    mov v1, #1
    push {v1}
    ldr v1, [fp, #24]
    push {v1}
    bl Test_0(PLT)
    add sp, sp, #44
    mov v1, a1

    b Main_0_exit

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

