package com.btec.cms.common;

public class BaseConstants {

  /*---------------------------------------------------------------------
   *                     Date format pattern
   *-------------------------------------------------------------------*/
  /** DATE FORMAT ORA YMDHMS */
  public static final String PATTERN_ORA_DATE_YMDHMS_FORMAT = "yyyy/mm/dd hh24:mi:ss";
  /** TIMESTAMP pattern for ORACLE */
  public static final String PATTERN_ORA_TIMESTAMP = "yy-MM-dd HH:mm:ss.SSS";

  public static final String PATTERN_ORA_TIMESTAMP_A_S = "yy/MM/dd HH:mm:ss.SSS";
  public static final String PATTERN_ORA_TIMESTAMP_B = "yy-MM-dd HH:mm:ss";
  public static final String PATTERN_ORA_TIMESTAMP_B_S = "yy/MM/dd HH:mm:ss";
  public static final String PATTERN_ORA_TIMESTAMP_C = "yy-MM-dd HH:mm";
  public static final String PATTERN_ORA_TIMESTAMP_C_S = "yy/MM/dd HH:mm";
  public static final String PATTERN_ORA_TIMESTAMP_D = "yy-MM-dd";
  public static final String PATTERN_ORA_TIMESTAMP_E = "yyyy-MM-dd HH:mm:ss";
  public static final String PATTERN_ORA_TIMESTAMP_E_S = "yyyy/MM/dd HH:mm:ss";

  public static final String PATTERN_TIMESTAMP_A = "yyyyMMdd HH:mm:ss.SSS";
  public static final String PATTERN_TIMESTAMP_A_HYPHEN = "yyyy-MM-dd HH:mm:ss.SSS";
  public static final String PATTERN_TIMESTAMP_A_SLASH = "yyyy/MM/dd HH:mm:ss.SSS";
  public static final String PATTERN_TIMESTAMP_B = "yyyyMMdd HH:mm:ss";
  public static final String PATTERN_TIMESTAMP_B_HYPHEN = "yyyy-MM-dd HH:mm:ss";
  public static final String PATTERN_TIMESTAMP_B_SLASH = "yyyy/MM/dd HH:mm:ss";
  public static final String PATTERN_TIMESTAMP_C = "yyyyMMdd HH:mm";
  public static final String PATTERN_TIMESTAMP_C_HYPHEN = "yyyy-MM-dd HH:mm";
  public static final String PATTERN_TIMESTAMP_C_SLASH = "yyyy/MM/dd HH:mm";
  public static final String PATTERN_TIMESTAMP_D = "yyyyMMdd";
  public static final String PATTERN_TIMESTAMP_D_HYPHEN = "yyyy-MM-dd";
  public static final String PATTERN_TIMESTAMP_D_SLASH = "yyyy/MM/dd";

  public static final String PATTERN_MONTH_DAY_YEAR = "MMM dd, yyyy";
  public static final String PATTERN_HMS = "HHmmss";
  public static final String PATTERN_TIMESTAMP_HM = "HH:mm";

  /*---------------------------------------------------------------------
   *                     USER STATUS
   *-------------------------------------------------------------------*/
  public static final int USER_STATUS_ACTIVE = 1;
  public static final int USER_STATUS_DISABLED = 0;
  public static final int USER_STATUS_NEW = -1;

  /*---------------------------------------------------------------------
   *                     ROLE ID
   *-------------------------------------------------------------------*/
  public static final Long ROLE_ID_ADMIN = 1L;
  public static final Long ROLE_ID_STAFF = 2L;
  public static final Long ROLE_ID_TEACHER = 3L;
  public static final Long ROLE_ID_STUDENT = 4L;

  /*---------------------------------------------------------------------
   *                      COURSE STATUS
   *-------------------------------------------------------------------*/
  public static final String COURSE_TEACHING = "Teaching";
  public static final String COURSE_DONE = "Done";
  public static final String COURSE_NEW = "New";
  // ***** constructor *****
  // ***** public method *****
  // ***** protected method *****
  // ***** private method *****
  // ***** call back method *****
  // ***** getter and setter *****

}
