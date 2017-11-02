# Agenda DevDay - MemoryLeaks in Vaadin

## Intro
What is a Memory Leak?
What kind of references are available in Java?



## How to detect Memory Leaks?
###  Show the DemoProject
overview of the code

### Profiling
#### start analyzing the app
??
#### Profiler VisualVM
create Snapshots
compare Snapshots

#### Profiler jProfiler (video) ??
-> Video ?

#### Profiler Yourkit - ??
Analyze Performance
Analyze Memory Snapshots
load Snapshots from VisualVM

### HeapDumps
Why HeapDumps
How to create HeapDumps
Analyze HeapDumps
* VisualVM
* Eclipse MAT

### start monitoring the app (AMP)
* perfino developer version : http://localhost:8020/
* stagemonitor


## How to solve Memory Leaks

### web links
[http://www.logicbig.com/tutorials/core-java-tutorial/gc/soft-vs-weak-ref/](http://www.logicbig.com/tutorials/core-java-tutorial/gc/soft-vs-weak-ref/)
[https://dzone.com/articles/finalization-and-phantom](https://dzone.com/articles/finalization-and-phantom)

### the generic way
nothing new for Java9
java.lang.ref.Reference
 * PhantomReference<T> since 1.2
 * SoftReference<T> since 1.2
 * WeakReference<T> since 1.2


#### WeakReference<T>
Is used to hold an object which will become eligible for the garbage collection 
as soon as it is not reachable by the program.

#### WeakHashMap

### SoftReference<T>
This lives longer as a WeakReference, it will only be garbage collected 
before an OutOfMemoryError is thrown.


### PhantomReference
to avoid using finalize

phantom reachable, phantomly reachable
An object is phantom reachable if it is neither strongly nor softly 
nor weakly reachable and has been finalized and there is a path from 
the roots to it that contains at least one phantom reference.

### ReferenceQueue
https://stackoverflow.com/questions/14450538/using-javas-referencequeue


## The Vaadin-Way

## Show Memory Leak
### Big Memory Leak 
Show with a few clicks that something is going wrong

### Small MemoryLeak - UI Scope / Session Scope
How to get this ?
let a long test run

## Vaadin specific
How to push Data without Memory Leak
How to remove Listeners

## Vaadin specific
https://new.vaadin.com/docs/framework/v8/advanced/advanced-global.html
