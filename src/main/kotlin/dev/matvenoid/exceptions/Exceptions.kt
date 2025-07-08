package dev.matvenoid.exceptions

class GroupNotFoundException(val id: Int) : Exception() {
    override val message: String = "Group $id does not exist"
}

class TagNotFoundException(val id: Int) : Exception() {
    override val message: String = "Tag $id does not exist"
}

class BookmarkNotFoundException(val id: Int) : Exception() {
    override val message: String = "Bookmark $id does not exist"
}

class InvalidIdFormatException() : Exception() {
    override val message: String = "Invalid id format"
}