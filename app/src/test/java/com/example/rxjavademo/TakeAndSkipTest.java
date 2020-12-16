package com.example.rxjavademo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TakeAndSkipTest {
    @Test
    public void testTakeSkip() {
        Observable
                .range(1, 5)
                .take(3)
                .doFinally(() -> System.out.println("----------"))
                .subscribe(System.out::println);
        Observable.range(1, 5)
                .skip(3)
                .doFinally(() -> System.out.println("----------"))
                .subscribe(System.out::println);
        Observable
                .range(1, 5)
                .skip(5)
                .doFinally(() -> System.out.println("----------"))
                .subscribe(System.out::println);
    }

    @Test
    public void testFilter() {
        List list = new ArrayList();
        Observable
                .just("#tom", 6, "@jack", 5.6, 5, list)
                .filter(o -> o instanceof String && ((String) o).startsWith("@") || o instanceof Integer)
                .groupBy(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Exception {
                        return o.getClass().getName();
                    }
                })
                .subscribe(new Consumer<GroupedObservable<Object, Object>>() {

                    @Override
                    public void accept(GroupedObservable<Object, Object> objectObjectGroupedObservable) throws Exception {
                        System.out.println("group key:" + objectObjectGroupedObservable.getKey() + " value:" + objectObjectGroupedObservable.toString());

                    }
                });
    }

    @Test
    public void testGroupBy() {
        Observable.range(1, 10)
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {

                        return integer % 3 + "";
                    }
                })
                .subscribe(new Consumer<GroupedObservable<String, Integer>>() {
                    @Override
                    public void accept(GroupedObservable<String, Integer> observable) throws Exception {


                        observable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {

                                System.out.println(observable.getKey() + " " + integer);

                            }
                        });
                    }
                });
    }


    @Test
    public void testScan() {
        Observable
                .range(2, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        System.out.println("apply:" + integer + " " + integer2);
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer x) throws Exception {
                        System.out.println("accept:" + x);
                    }
                });
    }

    @Test
    public void testScan2() {
        Observable
                .range(2, 5)
                .scan(5, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        System.out.println("apply:" + integer + " " + integer2);
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer x) throws Exception {
                        System.out.println("accept:" + x);
                    }
                });
    }

    @Test
    public void testReduce() {
        Observable
                .range(2, 5)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        System.out.println("apply:" + integer + " " + integer2);
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer x) throws Exception {
                        System.out.println("accept:" + x);
                    }
                });
    }

    @Test
    public void testReduce2() {
        Observable
                .range(2, 5)
                .reduce(5, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        System.out.println("apply:" + integer + " " + integer2);
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer x) throws Exception {
                        System.out.println("accept:" + x);
                    }
                });
    }

    @Test
    public void testCollect() {
        Single<ArrayList<Integer>> all = Observable
                .range(10, 5)
                .reduce(new ArrayList<>(), new BiFunction<ArrayList<Integer>, Integer, ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> apply(ArrayList<Integer> list, Integer item) throws Exception {
                        list.add(item);
                        return list;
                    }
                });

        all.subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> objects) throws Exception {
                System.out.println(objects);
            }
        });

        Single<ArrayList<Integer>> collect = Observable
                .range(5, 5)
                .collect(ArrayList::new, ArrayList::add);
    }

    @Test
    public void testFlatmap() {
        Observable
                .range(5, 5)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just("the num is " + integer);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        System.out.println(o);
                    }
                });

    }
}