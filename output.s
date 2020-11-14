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
S1:
    .asciz "hello there!"

.text
.global main
.type main, %function

main:
    stmfd sp!,{fp,lr,v1,v2,v3,v4,v5}
    add fp,sp,#24
    ldr a1, =_int
    mov a2, #12345
    bl printf(PLT)
    ldr a1, =_string
    ldr a2, =S1
    bl printf(PLT)
    ldr a1, =_true
    bl printf(PLT)
    ldr a1, =_false
    bl printf(PLT)

.Main_0-exit:
    sub sp,fp,#24
    ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}



.end

