def makeIncreaser(more: Int) = (x: Int) => x - more
val inc1 = makeIncreaser(13)
inc1(20)

