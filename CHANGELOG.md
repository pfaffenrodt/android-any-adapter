# 1.5.5
* upgrade kotlin to 1.8.10
* upgrade dependency of build tools to 7.4.1
* upgrade gradle wrapper 7.5

# 1.5.4
* upgrade kotlin to 1.7.20
* upgrade dependency of build tools to 7.3.1
* upgrade dependency of recyclerView to 1.2.1
* upgrade dependency of paging to 3.1.1

# 1.5.3
* upgrade kotlin to 1.6.10

# 1.5.2
* upgrade kotlin to 1.5.21

# 1.5.1
* upgrade kotlin to 1.5.0
* upgrade dependency of build tools 4.2.0
* upgrade dependency of recyclerView to 1.2.0
* move to mavenCentral with sonatype

# 1.5.0

* add support for paging adapter with AnyPagingDataAdapter
* make ClassPresenterSelector open to inherit
* databinding presenter add support to pass handler as block function
* :boom: rename module from object-kotlin-adapter to any-adapter
* :fire: drop java implementation

# 1.4.0

* Migrate to androidx

# 1.3.0

Updated code to newer leanback.
* used ListUpdateCallback in array adapters diff callback
* replaced getItemCount() with size()
* drop deprecated getItem() (replaced with get())