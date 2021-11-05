from re import search, split
from urllib.request import Request, urlopen

from bs4 import BeautifulSoup

for n in range(1, 26):
    requests = Request('http://bang.dangdang.com/books/bestsellers/01.00.00.00.00.00-24hours-0-0-1-'+str(n), headers={
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36 Edg/95.0.1020.40'})
    response = urlopen(requests).read().decode('gb18030')
    soup = BeautifulSoup(response, 'html.parser')
    book_list = soup.find_all(class_=['name', 'publisher_info', 'price_r'])
    book_list = split(
        r'target="_blank" title="|">|<span class="price_r">¥|</span>,|" target="_blank">|</a></div>,|</span>]|<div class="publisher_info"><span>|</span> <a href="http://search.dangdang.com/\?key=', str(book_list))
    book_list = [i for i in book_list if i != ' ']
    count = 0
    tsql = 'insert into book (name, author, year, publisher, price) VALUES ('
    for i in range(0, len(book_list)):
        if(search(r'<|>|^ ', book_list[i])):
            continue
        elif(book_list[i] == book_list[i-1] or (book_list[i] == book_list[i-2] and count != 3)):
            continue
        elif(count == 2 and search(book_list[i], book_list[i-1])):
            continue
        elif(count == 2 and search(book_list[i], book_list[i-2])):
            continue
        elif(count == 2 and search(book_list[i], book_list[i-3])):
            continue
        else:
            tsql += "'"+book_list[i]+"'"
            count += 1
            if(count == 5):
                tsql += ");"
                print(tsql)
                count = 0
                tsql = 'insert into book (name, author, year, publisher, price) VALUES ('
            else:
                tsql += ', '
