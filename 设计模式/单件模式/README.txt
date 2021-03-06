单件模式定义：
	确保一个类只有一个实例，并提供一个全局访问点。
	
	实现方式：
		方式1：
			public class Singleton {
				// 在静态初始化器 (static ioitiatize)中创建单件，JVM会在加载这个类时马上创建此唯一的单件实例。
				// 保证了线程安全。
				private static Singleton uniqueInstance = new Singleton(); //唯一实例

				public static Singleton getInstance(){//全局访问点
					return uniqueInstance;
				}
			}
		方式2：
			public class Singleton {
				private volatile  static Singleton uniqueInstance;

				//利用双重检查加锁，确保只有第一次获取时才会同步。
				public static Singleton getInstance(){
					if (uniqueInstance == null){//第一重检查是为了提高效率吧
						synchronized (Singleton.class){
							if (uniqueInstance == null){
								uniqueInstance = new Singleton();
							}
						}
					}
					return uniqueInstance;
				}
			}
