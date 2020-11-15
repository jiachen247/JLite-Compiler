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
    .asciz "123\n"

    .text
    .global main
    .type main, %function

Test_0:
    stmfd sp!,{fp,lr,v1,v2,v3,v4,v5}
    add fp,sp,#24
    sub sp,fp,#4
    str a1, [fp, #24]
    mov a1,this
    mov a2,#1
    mov a3,#2
    mov a4,=S1
    bl Test_1(PLT)
    str ##8, [sp, #-4]!
    str ##7, [sp, #-4]!
    str ##6, [sp, #-4]!
    str ##5, [sp, #-4]!
    str ##4, [sp, #-4]!
    str ##3, [sp, #-4]!
    str ##2, [sp, #-4]!
    str ##1, [sp, #-4]!
    str #this, [sp, #-4]!
    bl Test_2(PLT)

Test_0_exit:
    sub sp,fp,#24
    ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

Test_1:
    stmfd sp!,{fp,lr,v1,v2,v3,v4,v5}
    add fp,sp,#24
    sub sp,fp,#16
    str a1, [fp, #24]
    str a2, [fp, #28]
    str a3, [fp, #32]
    str a4, [fp, #36]
    mov a1, #10
    b Test_1_exit

Test_1_exit:
    sub sp,fp,#24
    ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

Test_2:
    stmfd sp!,{fp,lr,v1,v2,v3,v4,v5}
    add fp,sp,#24
    b Test_2_exit

Test_2_exit:
    sub sp,fp,#24
    ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

main:
    stmfd sp!,{fp,lr,v1,v2,v3,v4,v5}
    add fp,sp,#24
    b Main_0_exit

main_exit:
    sub sp,fp,#24
    ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

    .end

