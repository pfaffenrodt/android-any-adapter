# 1.5.1
* upgrade kotlin to 1.5.0
* upgrade dependency to build tools 4.2.0
* upgrade dependency to recyclerView to 1.2.0
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