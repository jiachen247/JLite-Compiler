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
S1:
    .asciz "hello there!\n"

    .text
    .global main
    .type main, %function

main:
    stmfd sp!,{fp,lr,v1,v2,v3,v4,v5}
    add fp,sp,#24
    ldr a1, =_int
    mov a2, #123
    bl printf(PLT)
    ldr a1, =_string
    ldr a2, =S1
    bl printf(PLT)
    ldr a1, =_true
    bl printf(PLT)
    ldr a1, =_false
    bl printf(PLT)

main_exit:
    sub sp,fp,#24
    ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

    .end

