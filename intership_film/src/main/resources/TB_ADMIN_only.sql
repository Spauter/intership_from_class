/*
 Navicat Premium Data Transfer

 Source Server         : localhost_1521_ORCL
 Source Server Type    : Oracle
 Source Server Version : 110200
 Source Host           : localhost:1521
 Source Schema         : SCOTT

 Target Server Type    : Oracle
 Target Server Version : 110200
 File Encoding         : 65001

 Date: 28/08/2023 22:55:44
*/


-- ----------------------------
-- Table structure for TB_ADMIN
-- ----------------------------
DROP TABLE "SCOTT"."TB_ADMIN";
CREATE TABLE "SCOTT"."TB_ADMIN" (
  "AID" NUMBER NOT NULL,
  "ANAME" VARCHAR2(50 BYTE),
  "PWD" VARCHAR2(100 BYTE) DEFAULT '12345678' --密码
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;

-- ----------------------------
-- Table structure for TB_GRADES
-- ----------------------------
DROP TABLE "SCOTT"."TB_GRADES";
CREATE TABLE "SCOTT"."TB_GRADES" (
  "S_ID" NUMBER,
  "C_NAME" VARCHAR2(50 BYTE),
  "S_NAME" VARCHAR2(50 BYTE),
  "GRADE" NUMBER
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;

-- ----------------------------
-- Table structure for TB_STUDENT
-- ----------------------------
DROP TABLE "SCOTT"."TB_STUDENT";
CREATE TABLE "SCOTT"."TB_STUDENT" (
  "S_ID" NUMBER NOT NULL,
  "S_NAME" VARCHAR2(20 BYTE),
  "S_CLASS" VARCHAR2(30 BYTE),
  "S_IS_MEMBER" VARCHAR2(10 BYTE),
  "S_CLASS_POSITION" VARCHAR2(50 BYTE),
  "S_IMAGE" BLOB,
  "S_PHONE_NUMBER" VARCHAR2(20 BYTE),
  "S_PASSWORD" VARCHAR2(50 BYTE),
  "S_DATE" NUMBER,
  "S_SEX" VARCHAR2(6 BYTE)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;

-- ----------------------------
-- Table structure for TB_STUDENT_COURSES
-- ----------------------------
DROP TABLE "SCOTT"."TB_STUDENT_COURSES";
CREATE TABLE "SCOTT"."TB_STUDENT_COURSES" (
  "S_ID" NUMBER,
  "C_ID" NUMBER NOT NULL,
  "T_NAME" VARCHAR2(10 BYTE)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;

-- ----------------------------
-- Table structure for TB_TEACHER
-- ----------------------------
DROP TABLE "SCOTT"."TB_TEACHER";
CREATE TABLE "SCOTT"."TB_TEACHER" (
  "T_ID" NUMBER NOT NULL,
  "T_NAME" VARCHAR2(20 BYTE),
  "T_CNAME" VARCHAR2(50 BYTE),
  "POSITION" VARCHAR2(100 BYTE),
  "HIRE_DATE" VARCHAR2(30 BYTE),
  "T_PHONE_NUMBER" NUMBER,
  "T_PWD" NUMBER,
  "PERSONAL_INTRO" VARCHAR2(500 BYTE)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;

-- ----------------------------
-- Primary Key structure for table TB_ADMIN
-- ----------------------------
ALTER TABLE "SCOTT"."TB_ADMIN" ADD CONSTRAINT "SYS_C0010832" PRIMARY KEY ("AID");

-- ----------------------------
-- Uniques structure for table TB_ADMIN
-- ----------------------------
ALTER TABLE "SCOTT"."TB_ADMIN" ADD CONSTRAINT "SYS_C0010833" UNIQUE ("ANAME") NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Checks structure for table TB_GRADES
-- ----------------------------
ALTER TABLE "SCOTT"."TB_GRADES" ADD CONSTRAINT "CK_GRADE_RANGE" CHECK (grade >= 0 and grade <= 100) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table TB_STUDENT
-- ----------------------------
ALTER TABLE "SCOTT"."TB_STUDENT" ADD CONSTRAINT "SYS_C0010838" PRIMARY KEY ("S_ID");

-- ----------------------------
-- Checks structure for table TB_STUDENT
-- ----------------------------
ALTER TABLE "SCOTT"."TB_STUDENT" ADD CONSTRAINT "SYS_C0010837" CHECK (s_is_member in ('是', '否')) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table TB_STUDENT_COURSES
-- ----------------------------
ALTER TABLE "SCOTT"."TB_STUDENT_COURSES" ADD CONSTRAINT "SYS_C0010830" PRIMARY KEY ("C_ID");

-- ----------------------------
-- Primary Key structure for table TB_TEACHER
-- ----------------------------
ALTER TABLE "SCOTT"."TB_TEACHER" ADD CONSTRAINT "SYS_C0010836" PRIMARY KEY ("T_ID");
