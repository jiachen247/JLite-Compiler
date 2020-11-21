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
S1:
    .asciz "I am a "
S2:
    .asciz " and my name is "
S3:
    .asciz ". I am "
S4:
    .asciz " years old. I go "
S5:
    .asciz " "
S6:
    .asciz "!!!"
S7:
    .asciz "Dog"
S8:
    .asciz "bark"
S9:
    .asciz "Cat"
S10:
    .asciz "meow"
S11:
    .asciz "kitty cat"
S12:
    .asciz "doggy"
S13:
    .asciz "

"

    .text
    .global main
    .type main, %function

Animal_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v3, a1
    mov v2, a2
    mov v4, a3
    mov v1, a4
    mov a2, v3
    mov a1, v2
    str a1, [a2, #4]
    mov a2, v3
    mov a1, v4
    str a1, [a2, #12]
    mov a2, v3
    mov a1, v1
    str a1, [a2, #16]

Animal_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Animal_1:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v2, a1
    mov v1, a2
    mov a2, v2
    mov a1, v1
    str a1, [a2, #8]

Animal_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Animal_2:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v3, a1
    ldr v1, [v3, #4]
    mov a2, v1
    ldr a1, =S1
    push {a1, a2}
    push {a2}
    bl strlen(PLT)
    pop {a2}
    push { a1 }
    mov a1, a2
    bl strlen(PLT)
    pop {a2}
    add a1, a1, a2
    bl malloc(PLT)
    pop {a2}
    push {a1}
    bl strcpy(PLT)
    pop {a2, a1}
    bl strcat(PLT)
    mov v2, a1
    ldr a2, =S2
    mov a1, v2
    push {a1, a2}
    push {a2}
    bl strlen(PLT)
    pop {a2}
    push { a1 }
    mov a1, a2
    bl strlen(PLT)
    pop {a2}
    add a1, a1, a2
    bl malloc(PLT)
    pop {a2}
    push {a1}
    bl strcpy(PLT)
    pop {a2, a1}
    bl strcat(PLT)
    mov v1, a1
    ldr v2, [v3, #8]
    mov a2, v2
    mov a1, v1
    push {a1, a2}
    push {a2}
    bl strlen(PLT)
    pop {a2}
    push { a1 }
    mov a1, a2
    bl strlen(PLT)
    pop {a2}
    add a1, a1, a2
    bl malloc(PLT)
    pop {a2}
    push {a1}
    bl strcpy(PLT)
    pop {a2, a1}
    bl strcat(PLT)
    mov v4, a1
    ldr a2, =S3
    mov a1, v4
    push {a1, a2}
    push {a2}
    bl strlen(PLT)
    pop {a2}
    push { a1 }
    mov a1, a2
    bl strlen(PLT)
    pop {a2}
    add a1, a1, a2
    bl malloc(PLT)
    pop {a2}
    push {a1}
    bl strcpy(PLT)
    pop {a2, a1}
    bl strcat(PLT)
    mov v1, a1
    mov a2, v1
    ldr a1, =_string
    bl printf(PLT)
    ldr v1, [v3, #16]
    mov a2, v1
    ldr a1, =_int
    bl printf(PLT)
    ldr a2, =S4
    ldr a1, =_string
    bl printf(PLT)
    ldr v2, [v3, #12]
    ldr a2, =S5
    mov a1, v2
    push {a1, a2}
    push {a2}
    bl strlen(PLT)
    pop {a2}
    push { a1 }
    mov a1, a2
    bl strlen(PLT)
    pop {a2}
    add a1, a1, a2
    bl malloc(PLT)
    pop {a2}
    push {a1}
    bl strcpy(PLT)
    pop {a2, a1}
    bl strcat(PLT)
    mov v1, a1
    mov a2, v1
    ldr a1, =_string
    bl printf(PLT)
    ldr v2, [v3, #12]
    ldr a2, =S5
    mov a1, v2
    push {a1, a2}
    push {a2}
    bl strlen(PLT)
    pop {a2}
    push { a1 }
    mov a1, a2
    bl strlen(PLT)
    pop {a2}
    add a1, a1, a2
    bl malloc(PLT)
    pop {a2}
    push {a1}
    bl strcpy(PLT)
    pop {a2, a1}
    bl strcat(PLT)
    mov v1, a1
    mov a2, v1
    ldr a1, =_string
    bl printf(PLT)
    ldr v2, [v3, #12]
    ldr a2, =S6
    mov a1, v2
    push {a1, a2}
    push {a2}
    bl strlen(PLT)
    pop {a2}
    push { a1 }
    mov a1, a2
    bl strlen(PLT)
    pop {a2}
    add a1, a1, a2
    bl malloc(PLT)
    pop {a2}
    push {a1}
    bl strcpy(PLT)
    pop {a2, a1}
    bl strcat(PLT)
    mov v1, a1
    mov a2, v1
    ldr a1, =_string
    bl printf(PLT)

Animal_2_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Dog_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v2, a1
    mov v1, a2
    mov a2, #20
    mov a1, #1
    bl calloc(PLT)
    mov v3, a1
    mov a2, v2
    mov a1, v3
    str a1, [a2, #4]
    ldr v3, [v2, #4]
    mov a4, v1
    ldr a3, =S8
    ldr a2, =S7
    mov a1, v3
    bl Animal_0(PLT)


Dog_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Dog_1:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v2, a1
    mov v1, a2
    ldr v3, [v2, #4]
    mov a2, v3
    mov a1, v1
    str a1, [a2, #8]

Dog_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Dog_2:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v1, a1
    ldr v2, [v1, #4]
    mov a1, v2
    bl Animal_2(PLT)


Dog_2_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Cat_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v2, a1
    mov v1, a2
    mov a2, #20
    mov a1, #1
    bl calloc(PLT)
    mov v3, a1
    mov a2, v2
    mov a1, v3
    str a1, [a2, #4]
    ldr v3, [v2, #4]
    mov a4, v1
    ldr a3, =S10
    ldr a2, =S9
    mov a1, v3
    bl Animal_0(PLT)


Cat_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Cat_1:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v3, a1
    mov v2, a2
    ldr v1, [v3, #4]
    mov a2, v1
    mov a1, v2
    str a1, [a2, #8]

Cat_1_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

Cat_2:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v1, a1
    ldr v2, [v1, #4]
    mov a1, v2
    bl Animal_2(PLT)


Cat_2_exit:
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
    ldr a2, =#1
    mov a1, v2
    bl Cat_0(PLT)

    ldr a2, =S11
    mov a1, v2
    bl Cat_1(PLT)

    mov a2, #8
    mov a1, #1
    bl calloc(PLT)
    mov v1, a1
    ldr a2, =#2
    mov a1, v1
    bl Dog_0(PLT)

    ldr a2, =S12
    mov a1, v1
    bl Dog_1(PLT)

    mov a1, v2
    bl Cat_2(PLT)

    ldr a2, =S13
    ldr a1, =_string
    bl printf(PLT)
    mov a1, v1
    bl Dog_2(PLT)


Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

