import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.cart.loggedCart.ProductsItem

class CartDiffCallback(
    private val oldList: List<ProductsItem?>,
    private val newList: List<ProductsItem?>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem?.product?.id == newItem?.product?.id // Check for product ID equality
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem?.equals(newItem) ?: (newItem == null) // Check for object equality or null handling
    }
}