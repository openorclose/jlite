.data
.int_format:
	.asciz "%i\n"
.string_format:
	.asciz "%s\n"
.true:
	.asciz "true"
.false:
	.asciz "false"
	.text
	.global main
_Overloaded_0:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #40
	str a1, [fp, #-28]
	mov a2, #1
	bl _Overloaded_1
	mov v1, a1
	str v1, [fp, #-32]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	ldr a1, [fp, #-28]
	mov a2, #2
	mov a3, #4
	bl _Overloaded_2
	mov v1, a1
	str v1, [fp, #-36]
	mov a2, v1
	ldr a1, =.int_format
	bl printf(PLT)
	ldr a1, [fp, #-28]
	mov a2, #1
	mov a3, #0
	bl _Overloaded_3
	mov v1, a1
	str v1, [fp, #-40]
	cmp v1, #1
	ldreq a2, =.true
	ldrne a2, =.false
	ldr a1, =.string_format
	bl printf(PLT)
_Overloaded_0exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

_Overloaded_1:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #32
	str a1, [fp, #-28]
	str a2, [fp, #-32]
	mov a1, a2
	b _Overloaded_1exit
_Overloaded_1exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

_Overloaded_2:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #40
	str a1, [fp, #-28]
	str a2, [fp, #-32]
	str a3, [fp, #-36]
	ldr v2, [fp, #-32]
	ldr v3, [fp, #-36]
	add v1, v2, v3
	str v1, [fp, #-40]
	mov a1, v1
	b _Overloaded_2exit
_Overloaded_2exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

_Overloaded_3:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #40
	str a1, [fp, #-28]
	str a2, [fp, #-32]
	str a3, [fp, #-36]
	ldr v2, [fp, #-32]
	ldr v3, [fp, #-36]
	orr v1, v2, v3
	str v1, [fp, #-40]
	mov a1, v1
	b _Overloaded_3exit
_Overloaded_3exit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

main:
	stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}
	add fp, sp, #24
	sub sp, fp, #40
	str a1, [fp, #-28]
	mov a1, #0
	bl _Znwj(PLT)
	mov v1, a1
	str v1, [fp, #-32]
	str v1, [fp, #-40]
	mov a1, v1
	bl _Overloaded_0
	mov v1, a1
	str v1, [fp, #-36]
mainexit:
	sub sp,fp,#24
	ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}

