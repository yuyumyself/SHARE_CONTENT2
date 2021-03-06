js遍历对象：
	var obj = {"name":"tom","sex":"male"};
	for(var a in obj){console.log(a);console.log(obj[a]);}
map对象：
	https://blog.csdn.net/qq_40144558/article/details/80775287
	https://www.cnblogs.com/mywangpingan/p/10309058.html
	
	map.forEach(function(value,key){
	　　console.log("value",value);  
	　　console.log("key",key);  
	});
/* eslint-disable */
const ObjectUtil = {
    //值复制（深拷贝）
    deepClone: function (obj) {
        //判断是对象，就进行循环复制
        if (typeof obj === 'object' && obj != null) {
            // 区分是数组还是对象，创建空的数组或对象
            const o = Object.prototype.toString.call(obj).slice(8, -1) === 'Array' ? [] : {};
            for (const k in obj) {
                // 如果属性对应的值为对象，则递归复制
                if (typeof obj[k] === 'object' && obj[k] != null) {
                    o[k] = this.deepClone(obj[k]);
                } else {
                    o[k] = obj[k];
                }
            }
			return o;	
        } else { //不为对象，直接把值返回
            return obj;
        }
    }
};
export { ObjectUtil };
// 使用时: import {ObjectUtil} from 'ObjectUtils.js'; ObjectUtil.deepClone();


