package metacall;

import com.sun.jna.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.nio.file.Paths;
import java.nio.file.Files;

class Utils
{

	public static class SizeT extends IntegerType
	{
		private static final long serialVersionUID = 1L;

		public SizeT()
	    {
	      this(0);
	    }

	    public SizeT(long value)
	    {
	      super(Native.SIZE_T_SIZE, value, true);
	    }
	}

	public static interface FunctionPointer extends Callback
	{
		Pointer callback(SizeT argc, Pointer args, Pointer data);
	}

	public static interface ResolveCallback extends Callback
	{
		Pointer invoke(Pointer result, Pointer data);
	}
	public static interface RejectCallback extends Callback
	{
		Pointer invoke(Pointer error, Pointer data);
	}

	// TODO
	//MetacallException
	//AllocationError
	//DestrucitonError

	boolean isNull(Pointer ptr)
	{
		return (ptr == null || ptr == Pointer.NULL)? true:false;
	}

	private static class PathCache {
		private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

		public String resolve(String pathStr) {
			return cache.computeIfAbsent(pathStr, k -> {
				try {
					return Paths.get(k).toAbsolutePath().toString();
				} catch (Exception e) {
					return null;
				}
			});
		}
	}

}
