FROM postgres:9.5
RUN apt-get update
RUN apt-get -y upgrade
RUN apt-get dist-upgrade
RUN apt-get purge $(dpkg -l | awk '/^rc/ { print $2 }')
RUN apt-get autoremove
RUN apt-get -y install git
RUN echo "deb http://http.debian.net/debian jessie-backports main" >>/etc/apt/sources.list
RUN echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" > /etc/apt/sources.list.d/webupd8team-java.list
RUN echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" >> /etc/apt/sources.list.d/webupd8team-java.list
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886
RUN apt-get update
ENV TERM xterm-256color
RUN apt-get -y install git
RUN apt-get -y install postgresql-server-dev-9.5 libpq-dev libecpg-dev
RUN apt-get -y install g++ libkrb5-dev
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN apt-get -y install oracle-java8-installer
RUN git clone https://github.com/tada/pljava.git
RUN export PGXS=/usr/lib/postgresql/9.5/lib/pgxs/src/makefiles/pgxs.mk
RUN export LD_LIBRARY_PATH="/usr/lib/jvm/java-8-oracle/jre/lib/amd64/server"

EXPOSE 5432

ENTRYPOINT ["docker-entrypoint.sh"]
CMD iptables -I INPUT -p tcp -m tcp --dport 5432 -j ACCEPT
CMD ["postgres"]
