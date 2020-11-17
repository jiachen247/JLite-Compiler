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
    .asciz "\n"
S1:
    .asciz "abc"
S2:
    .asciz "def"
S3:
    .asciz "jiachen weas here?!"

    .text
    .global main
    .type main, %function

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #52
    ldr v1, =S2
    mov v2, v1
    ldr v1, =S1
    mov v3, v1
    mov a1, v2
    bl strlen(PLT)
    mov v1, a1
    mov a1, v3
    bl strlen(PLT)
    add a1, a1, v1
    bl malloc(PLT)
    mov v1, a1
    mov a2, v3
    bl strcpy(PLT)
    mov a1, v1
    mov a2, v2
    bl strcat(PLT)
    mov v3, a1
    str v1, [fp, #-32]
    ldr v1, =S3
    mov v2, v1
    ldr v1, [fp, #-32]
    mov v3, v1
    mov a1, v2
    bl strlen(PLT)
    mov v1, a1
    mov a1, v3
    bl strlen(PLT)
    add a1, a1, v1
    bl malloc(PLT)
    mov v1, a1
    mov a2, v3
    bl strcpy(PLT)
    mov a1, v1
    mov a2, v2
    bl strcat(PLT)
    mov v3, a1
    str v1, [fp, #-36]
    ldr v1, [fp, #-36]
    mov a2, v1
    ldr a1, =_string
    bl printf(PLT)
    ldr v1, [fp, #-28]
    mov v4, v1
    ldr v1, [v4, #4]
    str v1, [fp, #-40]
    ldr v1, [fp, #-40]
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)
    mov a1, #8
    bl malloc(PLT)
    mov v1, a1
    str v1, [fp, #-28]
    ldr v1, [fp, #-28]
    mov v4, v1
    ldr v1, [v4, #4]
    str v1, [fp, #-44]
    ldr v1, [fp, #-44]
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)
    ldr v1, =_null
    str v1, [fp, #-28]
    ldr v1, [fp, #-28]
    mov v4, v1
    ldr v1, [v4, #4]
    str v1, [fp, #-48]
    ldr v1, [fp, #-48]
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)
    b MainC_0_exit

MainC_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

