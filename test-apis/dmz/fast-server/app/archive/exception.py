class NotFoundException(Exception):
    def __str__(self):
        return 'Details are not found.'
