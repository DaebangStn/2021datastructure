class Node_tree<K extends Comparable<K>, V extends Comparable<V>> extends LinkedList<V> {
    private Node_tree<K, V> left, right, parent;
    private K key;
    private int height;

    Node_tree(K key, V value) {
        super();
        this.parent = null;
        this.left = null;
        this.right = null;
        this.height = 0;
        this.key = key;
        this.add(value);
    }

    public final String keyToString() {
        String str_l = "";
        String str_r = "";
        if (this.getLeft() != null) {
            str_l = this.getLeft().keyToString();
        }
        if (this.getRight() != null) {
            str_r = this.getRight().keyToString();
        }
        return this.getKey() + " " + str_l + str_r;
    }

    public final void addItem(V obj) {
        super.add(obj);
    }

    public Node_tree<K, V> addNode(Node_tree<K, V> child) { // returns added node
        if (this.keyBiggerThan(child)) {
            if (this.getLeft() == null) {
                this.setLeft(child);
                child.setParent(this);
            } else {
                return this.getLeft().addNode(child);
            }
        } else {
            if (this.getRight() == null) {
                this.setRight(child);
                child.setParent(this);
            } else {
                return this.getRight().addNode(child);
            }
        }
        return child;
    }

    public Node_tree<K, V> findKey(K key) {
        if (this.keySmallerThan(key)) {
            if (this.getRight() == null) {
                return null;
            }
            return this.getRight().findKey(key);
        } else if (this.keyBiggerThan(key)) {
            if (this.getLeft() == null) {
                return null;
            }
            return this.getLeft().findKey(key);
        } else {
            return this;
        }
    }

    public final void refreshHeight() {
        if (this.getLeft() == null && this.getRight() == null) {
            this.height = 0;
        } else if (this.getRight() == null) {
            this.getLeft().refreshHeight();
            this.height = this.getLeft().getHeight() + 1;
        } else if (this.getLeft() == null) {
            this.getRight().refreshHeight();
            this.height = this.getRight().getHeight() + 1;
        } else {
            this.getLeft().refreshHeight();
            this.getRight().refreshHeight();
            this.height = Math.max(this.getRight().getHeight(), this.getLeft().getHeight()) + 1;
        }
    }

    public final void balance() { // recursive rebalance until root comes
        this.refreshHeight();
        int bf = this.getBF();
        if (bf == 2) {
            if (this.getLeft().getBF() == 1) { // LL state
                this.rotateLL();
            } else { // LR state
                this.getLeft().rotateRR();
                this.rotateLL();
            }
        } else if (bf == -2) {
            if (this.getRight().getBF() == -1) { // RR state
                this.rotateRR();
            } else { // RL state
                this.getRight().rotateLL();
                this.rotateRR();
            }
        }
        if (this.getParent() == null) {
            return;
        }
        this.getParent().balance();
    }

    public final void rotateLL() {
        Node_tree<K, V> node_l = this.getLeft();
        this.setLeft(node_l.getRight());
        if (node_l.getRight() != null) {
            node_l.getRight().setParent(this);
        }
        this.parentChildChange(node_l);
        node_l.setParent(this.getParent());
        this.setParent(node_l);
        node_l.setRight(this);
    }

    public final void rotateRR() {
        Node_tree<K, V> node_r = this.getRight();
        this.setRight(node_r.getLeft());
        if (node_r.getLeft() != null) {
            node_r.getLeft().setParent(this);
        }
        this.parentChildChange(node_r);
        node_r.setParent(this.getParent());
        this.setParent(node_r);
        node_r.setLeft(this);
    }

    public final void setLeft(Node_tree<K, V> obj) {
        this.left = obj;
    }

    public final void setRight(Node_tree<K, V> obj) {
        this.right = obj;
    }

    public final void setParent(Node_tree<K, V> obj) {
        this.parent = obj;
    }

    public final Node_tree<K, V> getLeft() {
        return this.left;
    }

    public final Node_tree<K, V> getRight() {
        return this.right;
    }

    public final Node_tree<K, V> getParent() {
        return this.parent;
    }

    public final K getKey() {
        return this.key;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getBF() {
        return this.getHeightL() - this.getHeightR();
    }

    private int getHeightL() {
        if (this.getLeft() == null) {
            return -1;
        }
        return this.getLeft().getHeight();
    }

    private int getHeightR() {
        if (this.getRight() == null) {
            return -1;
        }
        return this.getRight().getHeight();
    }

    public final boolean keyBiggerThan(Node_tree<K, V> obj) {
        if (obj == null) {
            return false;
        }
        if (this.getKey().compareTo(obj.getKey()) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean keySmallerThan(Node_tree<K, V> obj) {
        if (obj == null) {
            return false;
        }
        if (this.getKey().compareTo(obj.getKey()) < 0) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean keySame(Node_tree<K, V> obj) {
        if (obj == null) {
            return false;
        }
        if (this.getKey().compareTo(obj.getKey()) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean keyBiggerThan(K key) {
        if (this.getKey().compareTo(key) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean keySmallerThan(K key) {
        if (this.getKey().compareTo(key) < 0) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean keySame(K key) {
        if (this.getKey().compareTo(key) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public final void parentChildChange(Node_tree<K, V> obj) {
        if (this.getParent() == null) {
            return;
        }
        if (this.getParent().getLeft() == this) {
            this.getParent().setLeft(obj);
        } else {
            this.getParent().setRight(obj);
        }
    }
}
