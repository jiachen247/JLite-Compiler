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
    .asciz "false"
S2:
    .asciz "true"

    .text
    .global main
    .type main, %function

BoolOps_0:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    mov v2, a1
    mov v1, a2
    ldr a2, =#6
    add a1, v1, a2
    b BoolOps_0_exit

BoolOps_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #28
    ldr v5, =#4
    ldr v4, =#5
    mov a3, #1
    mov v3, #0
    and v1, v3, a3
    orr v2, v3, v1
    orr v1, v3, a3
    orr v3, v1, v2
    mov v1, #0
    cmp v4, v5
    movgt v1, #1
    and a3, v3, v1
    mov v1, #0
    cmp v4, v5
    movgt v1, #1
    and v2, v3, v1
    cmp v2, #1
    beq l1
    push {a3}
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
    b l2
l1:
    push {a3}
    ldr a2, =S2
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
l2:
    mov v1, #0
    cmp v4, v5
    movlt v1, #1
    orr v2, v1, v3
    cmp v2, #1
    beq l3
    push {a3}
    ldr a2, =S1
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
    b l4
l3:
    push {a3}
    ldr a2, =S2
    ldr a1, =_string
    bl printf(PLT)
    pop {a3}
l4:

BoolOpsMain_0_exit:
    mov a1, #0
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end
