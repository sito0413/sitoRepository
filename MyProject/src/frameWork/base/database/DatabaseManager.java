package frameWork.base.database;

import java.math.BigDecimal;
import java.math.BigInteger;
import frameWork.base.database.DatabaseManager.d1.t1.t1Row;
import frameWork.base.database.DatabaseManager.d1.t2.t2Row;
import frameWork.base.database.DatabaseManager.d3.f1.f1Row;
import frameWork.base.database.DatabaseManager.d3.f2.f2Row;
import frameWork.base.database.DatabaseManager.d3.f3.f3Row;
import frameWork.base.database.DatabaseManager.d3.f4.f4Row;
import frameWork.base.database.scheme.*;

/*
 * このソースは database配下のファイル群 から自動生成されました。
 * 原則このソースは修正しないでください
 * 作成日時 Fri May 09 11:37:06 JST 2014
 */
@SuppressWarnings("hiding")
public final class DatabaseManager{

	/**
	 * 
	 */
	public static final class d1 extends Database{
		d1(){
			super("d1");
		}

		/**
		 * 
		 */
		public static final class t1 extends Table<t1Row >{
			t1(){
				super("d1.t1");
			}

			/**
			 * 
			 */
			public static final class id extends Field<Integer>{
				id(){
					super("d1.t1.id", true, Type.INTEGER, true, false, "0");
				}
			}

			/**
			 * test1
			 */
			public static final class f1 extends Field<String>{
				f1(){
					super("d1.t1.f1", false, Type.TEXT, true, true, "test" );
				}
			}

			/**
			 * test2
			 */
			public static final class f2 extends Field<BigInteger>{
				f2(){
					super("d1.t1.f2", false, Type.INTEGER, false, false, "1" );
				}
			}

			/**
			 * test3
			 */
			public static final class f3 extends Field<BigDecimal>{
				f3(){
					super("d1.t1.f3", false, Type.DOUBLE, false, true, "1.1" );
				}
			}

			/**
			 * test4
			 */
			public static final class f4 extends Field<String>{
				f4(){
					super("d1.t1.f4", false, Type.TEXT, false, true, "aaaaaaaaaaaaa" );
				}
			}

			/**
			 * test5
			 */
			public static final class f5 extends Field<String>{
				f5(){
					super("d1.t1.f5", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test6
			 */
			public static final class f6 extends Field<String>{
				f6(){
					super("d1.t1.f6", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test7
			 */
			public static final class f7 extends Field<String>{
				f7(){
					super("d1.t1.f7", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test8
			 */
			public static final class f8 extends Field<String>{
				f8(){
					super("d1.t1.f8", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test9
			 */
			public static final class f9 extends Field<String>{
				f9(){
					super("d1.t1.f9", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test10
			 */
			public static final class f10 extends Field<String>{
				f10(){
					super("d1.t1.f10", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test11
			 */
			public static final class f11 extends Field<String>{
				f11(){
					super("d1.t1.f11", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test12
			 */
			public static final class f12 extends Field<String>{
				f12(){
					super("d1.t1.f12", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test13
			 */
			public static final class f13 extends Field<String>{
				f13(){
					super("d1.t1.f13", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test14
			 */
			public static final class f14 extends Field<String>{
				f14(){
					super("d1.t1.f14", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test15
			 */
			public static final class f15 extends Field<String>{
				f15(){
					super("d1.t1.f15", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test16
			 */
			public static final class f16 extends Field<String>{
				f16(){
					super("d1.t1.f16", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test17
			 */
			public static final class f17 extends Field<String>{
				f17(){
					super("d1.t1.f17", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test18
			 */
			public static final class f18 extends Field<String>{
				f18(){
					super("d1.t1.f18", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test19
			 */
			public static final class f19 extends Field<String>{
				f19(){
					super("d1.t1.f19", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test20
			 */
			public static final class f20 extends Field<String>{
				f20(){
					super("d1.t1.f20", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test21
			 */
			public static final class f21 extends Field<String>{
				f21(){
					super("d1.t1.f21", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test22
			 */
			public static final class f22 extends Field<String>{
				f22(){
					super("d1.t1.f22", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test23
			 */
			public static final class f23 extends Field<String>{
				f23(){
					super("d1.t1.f23", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test24
			 */
			public static final class f24 extends Field<String>{
				f24(){
					super("d1.t1.f24", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test25
			 */
			public static final class f25 extends Field<String>{
				f25(){
					super("d1.t1.f25", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test26
			 */
			public static final class f26 extends Field<String>{
				f26(){
					super("d1.t1.f26", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test27
			 */
			public static final class f27 extends Field<String>{
				f27(){
					super("d1.t1.f27", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test28
			 */
			public static final class f28 extends Field<String>{
				f28(){
					super("d1.t1.f28", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test29
			 */
			public static final class f29 extends Field<String>{
				f29(){
					super("d1.t1.f29", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test30
			 */
			public static final class f30 extends Field<String>{
				f30(){
					super("d1.t1.f30", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test31
			 */
			public static final class f31 extends Field<String>{
				f31(){
					super("d1.t1.f31", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test32
			 */
			public static final class f32 extends Field<String>{
				f32(){
					super("d1.t1.f32", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test33
			 */
			public static final class f33 extends Field<String>{
				f33(){
					super("d1.t1.f33", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test34
			 */
			public static final class f34 extends Field<String>{
				f34(){
					super("d1.t1.f34", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test35
			 */
			public static final class f35 extends Field<String>{
				f35(){
					super("d1.t1.f35", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test36
			 */
			public static final class f36 extends Field<String>{
				f36(){
					super("d1.t1.f36", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test37
			 */
			public static final class f37 extends Field<String>{
				f37(){
					super("d1.t1.f37", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test38
			 */
			public static final class f38 extends Field<String>{
				f38(){
					super("d1.t1.f38", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test39
			 */
			public static final class f39 extends Field<String>{
				f39(){
					super("d1.t1.f39", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test40
			 */
			public static final class f40 extends Field<String>{
				f40(){
					super("d1.t1.f40", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test41
			 */
			public static final class f41 extends Field<String>{
				f41(){
					super("d1.t1.f41", false, Type.TEXT, false, true, "" );
				}
			}

			/**
			 * test42
			 */
			public static final class f42 extends Field<String>{
				f42(){
					super("d1.t1.f42", false, Type.TEXT, false, true, "" );
				}
			}
			public static final class t1Row extends Row{
				/**
				 * id
				 */
				public static final id id = new id();
				/**
test1
				 */
				public static final f1 f1 = new f1();
				/**
test2
				 */
				public static final f2 f2 = new f2();
				/**
test3
				 */
				public static final f3 f3 = new f3();
				/**
test4
				 */
				public static final f4 f4 = new f4();
				/**
test5
				 */
				public static final f5 f5 = new f5();
				/**
test6
				 */
				public static final f6 f6 = new f6();
				/**
test7
				 */
				public static final f7 f7 = new f7();
				/**
test8
				 */
				public static final f8 f8 = new f8();
				/**
test9
				 */
				public static final f9 f9 = new f9();
				/**
test10
				 */
				public static final f10 f10 = new f10();
				/**
test11
				 */
				public static final f11 f11 = new f11();
				/**
test12
				 */
				public static final f12 f12 = new f12();
				/**
test13
				 */
				public static final f13 f13 = new f13();
				/**
test14
				 */
				public static final f14 f14 = new f14();
				/**
test15
				 */
				public static final f15 f15 = new f15();
				/**
test16
				 */
				public static final f16 f16 = new f16();
				/**
test17
				 */
				public static final f17 f17 = new f17();
				/**
test18
				 */
				public static final f18 f18 = new f18();
				/**
test19
				 */
				public static final f19 f19 = new f19();
				/**
test20
				 */
				public static final f20 f20 = new f20();
				/**
test21
				 */
				public static final f21 f21 = new f21();
				/**
test22
				 */
				public static final f22 f22 = new f22();
				/**
test23
				 */
				public static final f23 f23 = new f23();
				/**
test24
				 */
				public static final f24 f24 = new f24();
				/**
test25
				 */
				public static final f25 f25 = new f25();
				/**
test26
				 */
				public static final f26 f26 = new f26();
				/**
test27
				 */
				public static final f27 f27 = new f27();
				/**
test28
				 */
				public static final f28 f28 = new f28();
				/**
test29
				 */
				public static final f29 f29 = new f29();
				/**
test30
				 */
				public static final f30 f30 = new f30();
				/**
test31
				 */
				public static final f31 f31 = new f31();
				/**
test32
				 */
				public static final f32 f32 = new f32();
				/**
test33
				 */
				public static final f33 f33 = new f33();
				/**
test34
				 */
				public static final f34 f34 = new f34();
				/**
test35
				 */
				public static final f35 f35 = new f35();
				/**
test36
				 */
				public static final f36 f36 = new f36();
				/**
test37
				 */
				public static final f37 f37 = new f37();
				/**
test38
				 */
				public static final f38 f38 = new f38();
				/**
test39
				 */
				public static final f39 f39 = new f39();
				/**
test40
				 */
				public static final f40 f40 = new f40();
				/**
test41
				 */
				public static final f41 f41 = new f41();
				/**
test42
				 */
				public static final f42 f42 = new f42();
				@Override
				public final Field<?>[] getFields(){
					return new Field<?>[]{
						id,
						f1,
						f2,
						f3,
						f4,
						f5,
						f6,
						f7,
						f8,
						f9,
						f10,
						f11,
						f12,
						f13,
						f14,
						f15,
						f16,
						f17,
						f18,
						f19,
						f20,
						f21,
						f22,
						f23,
						f24,
						f25,
						f26,
						f27,
						f28,
						f29,
						f30,
						f31,
						f32,
						f33,
						f34,
						f35,
						f36,
						f37,
						f38,
						f39,
						f40,
						f41,
						f42,
					};
				}
			}
			/**id*/
			public static final id id = new id();
			/**
test1
			 */
			public static final f1 f1 = new f1();
			/**
test2
			 */
			public static final f2 f2 = new f2();
			/**
test3
			 */
			public static final f3 f3 = new f3();
			/**
test4
			 */
			public static final f4 f4 = new f4();
			/**
test5
			 */
			public static final f5 f5 = new f5();
			/**
test6
			 */
			public static final f6 f6 = new f6();
			/**
test7
			 */
			public static final f7 f7 = new f7();
			/**
test8
			 */
			public static final f8 f8 = new f8();
			/**
test9
			 */
			public static final f9 f9 = new f9();
			/**
test10
			 */
			public static final f10 f10 = new f10();
			/**
test11
			 */
			public static final f11 f11 = new f11();
			/**
test12
			 */
			public static final f12 f12 = new f12();
			/**
test13
			 */
			public static final f13 f13 = new f13();
			/**
test14
			 */
			public static final f14 f14 = new f14();
			/**
test15
			 */
			public static final f15 f15 = new f15();
			/**
test16
			 */
			public static final f16 f16 = new f16();
			/**
test17
			 */
			public static final f17 f17 = new f17();
			/**
test18
			 */
			public static final f18 f18 = new f18();
			/**
test19
			 */
			public static final f19 f19 = new f19();
			/**
test20
			 */
			public static final f20 f20 = new f20();
			/**
test21
			 */
			public static final f21 f21 = new f21();
			/**
test22
			 */
			public static final f22 f22 = new f22();
			/**
test23
			 */
			public static final f23 f23 = new f23();
			/**
test24
			 */
			public static final f24 f24 = new f24();
			/**
test25
			 */
			public static final f25 f25 = new f25();
			/**
test26
			 */
			public static final f26 f26 = new f26();
			/**
test27
			 */
			public static final f27 f27 = new f27();
			/**
test28
			 */
			public static final f28 f28 = new f28();
			/**
test29
			 */
			public static final f29 f29 = new f29();
			/**
test30
			 */
			public static final f30 f30 = new f30();
			/**
test31
			 */
			public static final f31 f31 = new f31();
			/**
test32
			 */
			public static final f32 f32 = new f32();
			/**
test33
			 */
			public static final f33 f33 = new f33();
			/**
test34
			 */
			public static final f34 f34 = new f34();
			/**
test35
			 */
			public static final f35 f35 = new f35();
			/**
test36
			 */
			public static final f36 f36 = new f36();
			/**
test37
			 */
			public static final f37 f37 = new f37();
			/**
test38
			 */
			public static final f38 f38 = new f38();
			/**
test39
			 */
			public static final f39 f39 = new f39();
			/**
test40
			 */
			public static final f40 f40 = new f40();
			/**
test41
			 */
			public static final f41 f41 = new f41();
			/**
test42
			 */
			public static final f42 f42 = new f42();
			@Override
			public final Field<?>[] getFields(){
				return new Field<?>[]{
					id,
					f1,
					f2,
					f3,
					f4,
					f5,
					f6,
					f7,
					f8,
					f9,
					f10,
					f11,
					f12,
					f13,
					f14,
					f15,
					f16,
					f17,
					f18,
					f19,
					f20,
					f21,
					f22,
					f23,
					f24,
					f25,
					f26,
					f27,
					f28,
					f29,
					f30,
					f31,
					f32,
					f33,
					f34,
					f35,
					f36,
					f37,
					f38,
					f39,
					f40,
					f41,
					f42,
				};
			}
			@Override
			public final t1Row  createRow(){
				return new t1Row ();
			}
		}

		/**
		 * 
		 */
		public static final class t2 extends Table<t2Row >{
			t2(){
				super("d1.t2");
			}

			/**
			 * 
			 */
			public static final class id extends Field<Integer>{
				id(){
					super("d1.t2.id", true, Type.INTEGER, true, false, "0");
				}
			}
			public static final class t2Row extends Row{
				/**
				 * id
				 */
				public static final id id = new id();
				@Override
				public final Field<?>[] getFields(){
					return new Field<?>[]{
						id,
					};
				}
			}
			/**id*/
			public static final id id = new id();
			@Override
			public final Field<?>[] getFields(){
				return new Field<?>[]{
					id,
				};
			}
			@Override
			public final t2Row  createRow(){
				return new t2Row ();
			}
		}
		/**

		 */
		public static final t1 t1 = new t1();
		/**

		 */
		public static final t2 t2 = new t2();
		@Override
		public final Table<?>[] getTables(){
			return new Table<?>[]{
			t1,
			t2,
			};
		}
	}

	/**
	 * 
	 */
	public static final class d2 extends Database{
		d2(){
			super("d2");
		}
		@Override
		public final Table<?>[] getTables(){
			return new Table<?>[]{
			};
		}
	}

	/**
	 * 
	 */
	public static final class d3 extends Database{
		d3(){
			super("d3");
		}

		/**
		 * 
		 */
		public static final class f1 extends Table<f1Row >{
			f1(){
				super("d3.f1");
			}

			/**
			 * 
			 */
			public static final class id extends Field<Integer>{
				id(){
					super("d3.f1.id", true, Type.INTEGER, true, false, "0");
				}
			}
			public static final class f1Row extends Row{
				/**
				 * id
				 */
				public static final id id = new id();
				@Override
				public final Field<?>[] getFields(){
					return new Field<?>[]{
						id,
					};
				}
			}
			/**id*/
			public static final id id = new id();
			@Override
			public final Field<?>[] getFields(){
				return new Field<?>[]{
					id,
				};
			}
			@Override
			public final f1Row  createRow(){
				return new f1Row ();
			}
		}

		/**
		 * 
		 */
		public static final class f2 extends Table<f2Row >{
			f2(){
				super("d3.f2");
			}

			/**
			 * 
			 */
			public static final class id extends Field<Integer>{
				id(){
					super("d3.f2.id", true, Type.INTEGER, true, false, "0");
				}
			}
			public static final class f2Row extends Row{
				/**
				 * id
				 */
				public static final id id = new id();
				@Override
				public final Field<?>[] getFields(){
					return new Field<?>[]{
						id,
					};
				}
			}
			/**id*/
			public static final id id = new id();
			@Override
			public final Field<?>[] getFields(){
				return new Field<?>[]{
					id,
				};
			}
			@Override
			public final f2Row  createRow(){
				return new f2Row ();
			}
		}

		/**
		 * 
		 */
		public static final class f3 extends Table<f3Row >{
			f3(){
				super("d3.f3");
			}

			/**
			 * 
			 */
			public static final class id extends Field<Integer>{
				id(){
					super("d3.f3.id", true, Type.INTEGER, true, false, "0");
				}
			}
			public static final class f3Row extends Row{
				/**
				 * id
				 */
				public static final id id = new id();
				@Override
				public final Field<?>[] getFields(){
					return new Field<?>[]{
						id,
					};
				}
			}
			/**id*/
			public static final id id = new id();
			@Override
			public final Field<?>[] getFields(){
				return new Field<?>[]{
					id,
				};
			}
			@Override
			public final f3Row  createRow(){
				return new f3Row ();
			}
		}

		/**
		 * 
		 */
		public static final class f4 extends Table<f4Row >{
			f4(){
				super("d3.f4");
			}

			/**
			 * 
			 */
			public static final class id extends Field<Integer>{
				id(){
					super("d3.f4.id", true, Type.INTEGER, true, false, "0");
				}
			}
			public static final class f4Row extends Row{
				/**
				 * id
				 */
				public static final id id = new id();
				@Override
				public final Field<?>[] getFields(){
					return new Field<?>[]{
						id,
					};
				}
			}
			/**id*/
			public static final id id = new id();
			@Override
			public final Field<?>[] getFields(){
				return new Field<?>[]{
					id,
				};
			}
			@Override
			public final f4Row  createRow(){
				return new f4Row ();
			}
		}
		/**

		 */
		public static final f1 f1 = new f1();
		/**

		 */
		public static final f2 f2 = new f2();
		/**

		 */
		public static final f3 f3 = new f3();
		/**

		 */
		public static final f4 f4 = new f4();
		@Override
		public final Table<?>[] getTables(){
			return new Table<?>[]{
			f1,
			f2,
			f3,
			f4,
			};
		}
	}
	/**
	 * 
	 */
	public static final d1 d1 = new d1();

	/**
	 * 
	 */
	public static final d2 d2 = new d2();

	/**
	 * 
	 */
	public static final d3 d3 = new d3();

	public static final Database[] databases = new Database[]{
		d1,
		d2,
		d3,
	};
}
