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

    .text
    .global main
    .type main, %function

main:
    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}
    add fp, sp, #24
    sub sp, fp, #152
    ldr a1, =#1
    mov spill, a1
    ldr a1, =#2
    mov spill, a1
    ldr a1, =#3
    mov spill, a1
    ldr a1, =#4
    mov spill, a1
    ldr a1, =#5
    mov spill, a1
    ldr a1, =#6
    mov spill, a1
    ldr a1, =#7
    mov spill, a1
    ldr a1, =#8
    mov spill, a1
    mov a1, spill
    mov spill, a1
    mov a1, spill
    mov spill, a1
    mov a1, spill
    mov spill, a1
    mov a1, spill
    mov spill, a1
    mov a1, spill
    mov v3, a1
    mov a1, spill
    mov v4, a1
    mov a1, spill
    mov v5, a1
    mov a1, spill
    mov v2, a1
    mov a1, v5
    mov a2, a1
    mov a1, v2
    mov a3, a1
    add a1, a2, a3
    mov v1, a1
    mov a1, v4
    mov a2, a1
    mov a1, v1
    mov a3, a1
    add a1, a2, a3
    mov v2, a1
    mov a1, v3
    mov a2, a1
    mov a1, v2
    mov a3, a1
    add a1, a2, a3
    mov v1, a1
    mov a1, spill
    mov a2, a1
    mov a1, v1
    mov a3, a1
    add a1, a2, a3
    mov v2, a1
    mov a1, spill
    mov a2, a1
    mov a1, v2
    mov a3, a1
    add a1, a2, a3
    mov v1, a1
    mov a1, spill
    mov a2, a1
    mov a1, v1
    mov a3, a1
    add a1, a2, a3
    mov v2, a1
    mov a1, spill
    mov a2, a1
    mov a1, v2
    mov a3, a1
    add a1, a2, a3
    mov v1, a1
    mov a1, spill
    mov a2, a1
    mov a1, v1
    mov a3, a1
    add a1, a2, a3
    mov v2, a1
    mov a1, spill
    mov a2, a1
    mov a1, v2
    mov a3, a1
    add a1, a2, a3
    mov v1, a1
    mov a1, spill
    mov a2, a1
    mov a1, v1
    mov a3, a1
    add a1, a2, a3
    mov v2, a1
    mov a1, spill
    mov a2, a1
    mov a1, v2
    mov a3, a1
    add a1, a2, a3
    mov v1, a1
    mov a1, spill
    mov a2, a1
    mov a1, v1
    mov a3, a1
    add a1, a2, a3
    mov v2, a1
    mov a1, spill
    mov a2, a1
    mov a1, v2
    mov a3, a1
    add a1, a2, a3
    mov v1, a1
    mov a1, spill
    mov a2, a1
    mov a1, v1
    mov a3, a1
    add a1, a2, a3
    mov v2, a1
    mov a1, spill
    mov a2, a1
    mov a1, v2
    mov a3, a1
    add a1, a2, a3
    mov v1, a1
    mov a1, v1
    mov a2, a1
    ldr a1, =_int
    bl printf(PLT)

Main_0_exit:
    sub sp, fp, #24
    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}

    .end

