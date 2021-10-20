package extensions

fun <T> Iterable<T>.randomK():T = this.shuffled().first()